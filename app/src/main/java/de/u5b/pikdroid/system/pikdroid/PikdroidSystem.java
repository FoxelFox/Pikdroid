package de.u5b.pikdroid.system.pikdroid;

import java.util.TreeMap;

import de.u5b.pikdroid.component.Component;
import de.u5b.pikdroid.component.Energy;
import de.u5b.pikdroid.component.Movement;
import de.u5b.pikdroid.component.Pose;
import de.u5b.pikdroid.component.Visual;
import de.u5b.pikdroid.component.detect.DetectHint;
import de.u5b.pikdroid.component.detect.Detectable;
import de.u5b.pikdroid.component.detect.Detector;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.entity.Entity;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.EventTopic;
import de.u5b.pikdroid.system.ASystem;

/**
 * The Pikdroid Game System
 * Created by Foxel on 18.08.2014.
 */
public class PikdroidSystem extends ASystem {

    private TreeMap<Integer, Entity> spawnedPikdroids;
    private TreeMap<Integer, Entity> enemies;
    private TreeMap<Integer, Entity> spawnedFood;
    private Entity base;
    private boolean loaded;
    private Entity userTarget;

    public PikdroidSystem(Engine engine) {
        super(engine);

        // subscribe to Spawn new Pikdroids
        eventManager.subscribe(EventTopic.SPAWN_PIKDROID, this);
        eventManager.subscribe(EventTopic.ENTITY_DELETED, this);
        eventManager.subscribe(EventTopic.SET_USER_TARGET, this);

        spawnedPikdroids = new TreeMap<Integer, Entity>();
        spawnedFood = new TreeMap<Integer, Entity>();
        enemies = new TreeMap<Integer, Entity>();

        loaded = false;
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getEventTopic()) {
            case SPAWN_PIKDROID: onSpawnPikdroid(event); break;
            case ENTITY_DELETED: onEntityDeleted(event); break;
            case SET_USER_TARGET: onSetUserTarget(event); break;

        }
    }

    private void onSetUserTarget(Event event) {
        if(userTarget == null) {
            buildUserTarget();
        }

        Pose poseNew = (Pose)event.getEntity().getComponent(Component.Type.POSE);
        Pose poseTarget = (Pose)userTarget.getComponent(Component.Type.POSE);

        poseTarget.setPositionX(poseNew.getPositionX() * 10);
        poseTarget.setPositionY(poseNew.getPositionY() * 10);
    }

    public void update() {

        if(!loaded) {
            buildBase();
            loaded = true;
        }

        if (enemies.size() < (spawnedPikdroids.size() >> 1))
            spawnEnemy();

        if(spawnedFood.size() < 10)
            spawnFood();



        // TODO: this should be a runnable for the base entity
        Energy energyBase = (Energy)base.getComponent(Component.Type.ENERGY);
        if(spawnedPikdroids.size() < 100 && energyBase.isChargeFull()) {
            energyBase.discharge();
            buildPikdroid(((Pose)base.getComponent(Component.Type.POSE)).getCopy());
        }

        // update gui
        engine.setPikdroidCount(spawnedPikdroids.size());
    }

    private void onEntityDeleted(Event event) {
        spawnedFood.remove(event.getEntity().getID());
        spawnedPikdroids.remove(event.getEntity().getID());
        enemies.remove(event.getEntity().getID());
    }

    private void onSpawnPikdroid(Event event) {
        buildPikdroid(((Pose)event.getEntity().getComponent(Component.Type.POSE)).getCopy());
    }

    /**
     * Build a new Pikdroid
     * @param pose position
     * @return a new Pikdroid Entity
     */
    private void buildPikdroid(Pose pose) {
        final Entity pikdroid = new Entity();

        final Detector detector = new Detector();
        final Movement movement = new Movement(0.1f,8.0f,0.1f);
        final Energy energy = new Energy(200,100,100);


        pose.scale(10,10,1);
        pose.translate(0,0,-0.2f);

        pikdroid.addComponent(pose);
        pikdroid.addComponent(new Visual(new float[] { 0.5f,  1.0f, 0.0f, 1.0f },
                              Visual.Shading.UniformColor,
                              Visual.Geometry.Quad));
        pikdroid.addComponent(movement);
        pikdroid.addComponent(energy);
        pikdroid.addComponent(new Detectable(DetectHint.PIKDROID));
        pikdroid.addComponent(detector);

        final Entity randomTarget = new Entity();
        final Pose randomPose = new Pose();
        randomTarget.addComponent(randomPose);

        movement.setTarget(randomTarget);
        final boolean[] hasFood = {false};

        pikdroid.addCodeForEvent(EventTopic.NEW_POSE_SECTOR_REACHED, new Runnable() {
            @Override
            public void run() {
                if (!hasFood[0]) {

                    Entity food = detector.getDetection(DetectHint.FOOD);

                    if (userTarget != null) {
                        movement.setTarget(userTarget);
                    }
                    if (food != null) {
                        movement.setTarget(food);
                    }
                }
            }
        });

        // code when the Pikdroid has reached his move target
        pikdroid.addCodeForEvent(EventTopic.MOVE_TARGET_REACHED, new Runnable() {
            @Override
            public void run() {

                if (hasFood[0]) {
                    // Transfer Energy from Intelligence to Base
                    modifyRandom(randomPose, 20.0f);
                    movement.setTarget(randomTarget);
                    eventManager.publish(new Event(EventTopic.TRY_ENERGY_TRANSFER, pikdroid, base));
                } else if (movement.getTarget().equals(userTarget)) {
                    // Next target should be the users target
                    entityManager.delete(userTarget);
                    userTarget = null;
                    modifyRandom(randomPose, 20.0f);
                    movement.setTarget(randomTarget);
                } else if (movement.getTarget().hasComponent(Component.Type.ENERGY)) {
                    // Pikdroid is on a Entity that contains Energy -> Food
                    // Transfer Energy from Food to Pikdroid
                    Entity food = movement.getTarget();
                    modifyRandom(randomPose, 20.0f);
                    movement.setTarget(randomTarget);
                    eventManager.publish(new Event(EventTopic.TRY_ENERGY_TRANSFER, food, pikdroid));
                } else {
                    // Pikdroid has nothing detected -> set a new random target
                    modifyRandom(randomPose, 20.0f);
                    movement.setTarget(randomTarget);
                }
            }
        });

        // code when the Pikdroid has energy transferred
        pikdroid.addCodeForEvent(EventTopic.ON_ENERGY_TRANSFERRED, new Runnable() {
            @Override
            public void run() {
                if (energy.isChargeFull()) {
                    hasFood[0] = true;
                    movement.setTarget(base);
                } else {
                    hasFood[0] = false;
                }
            }
        });

        // finalize
        entityManager.add(pikdroid);
        spawnedPikdroids.put(pikdroid.getID(), pikdroid);
    }

    /**
     * Spawn more food.
     * Food is simply a Entity that contains Energy
     */
    private void spawnFood() {
        Entity food = new Entity();

        Pose pose = new Pose();
        pose.translate(randomValue(20.0f),randomValue(20.0f),0.8f);

        Visual vis = new Visual(new float[] { 1.0f,  0.75f, 0.0f, 1.0f },
                                Visual.Shading.UniformColor,
                                Visual.Geometry.Quad);
        vis.scale(0.5f,0.5f,1.0f);

        Energy energy = new Energy(100,100,0);
        Detectable detectable = new Detectable(DetectHint.FOOD);

        food.addComponent(pose);
        food.addComponent(vis);
        food.addComponent(energy);
        food.addComponent(detectable);

        entityManager.add(food);
        spawnedFood.put(food.getID(), food);
    }

    /**
     * Create the Pikdroid home base
     */
    private void buildBase() {
        base = new Entity();

        Pose pose = new Pose();
        pose.translate(0,0,0.9f);

        Detectable detectable = new Detectable(DetectHint.BASE);

        Energy energy = new Energy(1000,1000,800);

        Visual visual = new Visual(new float[] { 0.0f, 1.0f, 0.5f, 1.0f },
                                   Visual.Shading.UniformColor,
                                   Visual.Geometry.Quad);

        //visual.setGeometryName("circle");
        visual.scale(2.0f, 2.0f, 1.0f);

        base.addComponent(pose);
        base.addComponent(detectable);
        base.addComponent(visual);
        base.addComponent(energy);

        entityManager.add(base);
    }

    /**
     * The Pikdroid enemy entity
     */
    private void spawnEnemy() {
        final Entity enemy = new Entity();

        final Pose pose = new Pose();
        pose.translate(0,13,0.8f);

        Detectable detectable = new Detectable(DetectHint.ENEMY);
        final Detector detector = new Detector();

        final Visual visual = new Visual(new float[] { 1.0f, 0.0f, 0.25f, 1.0f },
                                         Visual.Shading.UniformColor,
                                         Visual.Geometry.Quad);
        visual.scale(1.0f, 1.0f, 1.0f);

        final Movement move = new Movement(0.09f,8.0f,0.3f);
        move.setDistanceToReach(0.3f);
        move.setTarget(randomTarget(5.0f));

        final Energy energy = new Energy(1000,100,0);

        enemy.addComponent(pose);
        enemy.addComponent(detectable);
        enemy.addComponent(visual);
        enemy.addComponent(move);
        enemy.addComponent(detector);
        enemy.addComponent(energy);

        final Entity randomTarget = new Entity();
        final Pose randomPose = new Pose();
        randomTarget.addComponent(randomPose);

        final Runnable findTarget = new Runnable() {
            @Override
            public void run() {
                Entity[] detections = detector.getDetections();
                if(detections[DetectHint.FOOD.ordinal()] != null) {
                    move.setTarget(detections[DetectHint.FOOD.ordinal()]);
                } else if (detections[DetectHint.PIKDROID.ordinal()] != null) {
                    move.setTarget(detections[DetectHint.PIKDROID.ordinal()]);
                }

            }
        };

        enemy.addCodeForEvent(EventTopic.MOVE_TARGET_REACHED, new Runnable() {
            @Override
            public void run() {
                if (move.getTarget().hasComponent(Component.Type.ENERGY)) {
                    Energy tEnergy = (Energy) move.getTarget().getComponent(Component.Type.ENERGY);
                    energy.charge(tEnergy.discharge());
                    energy.discharge(100);
                    entityManager.delete(move.getTarget());
                }
                modifyRandom(randomPose, 20.0f);
                move.setTarget(randomTarget);
            }
        });

        enemy.addCodeForEvent(EventTopic.NEW_POSE_SECTOR_REACHED, findTarget);

        entityManager.add(enemy);
        enemies.put(enemy.getID(),enemy);
    }

    private void buildUserTarget() {
        userTarget = new Entity();

        Visual visual = new Visual(new float[] { 0.0f, 0.5f, 1.0f, 1.0f },
                                   Visual.Shading.UniformColor,
                                   Visual.Geometry.Quad);
        visual.scale(1.0f, 1.0f, 1.0f);

        Pose pose = new Pose();
        pose.translate(0,0,0.9f);

        userTarget.addComponent(visual);
        userTarget.addComponent(pose);

        entityManager.add(userTarget);
    }

    private void modifyRandom(Pose pose, float range) {
        int oldX = (int)pose.getPositionX();
        int oldY = (int)pose.getPositionX();

        while (oldX == (int)pose.getPositionX()) {
            pose.setPositionX(randomValue(range));

        }
        while (oldY == (int)pose.getPositionY()) {
            pose.setPositionY(randomValue(range));
        }
    }

    private Entity randomTarget(float range) {
        Entity target = new Entity();
        Pose tPose = new Pose();
        tPose.translate(randomValue(range), randomValue(range),0);
        target.addComponent(tPose);
        return target;
    }

    private float randomValue(float range){
        return ((float)Math.random() - 0.5f) * range;
    }
}
