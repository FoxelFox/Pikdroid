package de.u5b.pikdroid.system.pikdroid;

import java.util.TreeMap;

import de.u5b.pikdroid.component.Component;
import de.u5b.pikdroid.component.Energy;
import de.u5b.pikdroid.component.Intelligence;
import de.u5b.pikdroid.component.Movement;
import de.u5b.pikdroid.component.Pose;
import de.u5b.pikdroid.component.Visual;
import de.u5b.pikdroid.component.detect.DetectHint;
import de.u5b.pikdroid.component.detect.Detectable;
import de.u5b.pikdroid.component.detect.Detector;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.entity.Entity;
import de.u5b.pikdroid.manager.entity.EntityCode;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.Topic;
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


    public PikdroidSystem(Engine engine) {
        super(engine);

        // subscribe to Spawn new Pikdroids
        eventManager.subscribe(Topic.SPAWN_PIKDROID, this);
        eventManager.subscribe(Topic.ENTITY_DELETED, this);


        spawnedPikdroids = new TreeMap<Integer, Entity>();
        spawnedFood = new TreeMap<Integer, Entity>();
        enemies = new TreeMap<Integer, Entity>();

        loaded = false;
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getTopic()) {
            case SPAWN_PIKDROID: onSpawnPikdroid(event); break;
            case ENTITY_DELETED: onEntityDeleted(event); break;

        }
    }

    public void update() {

        if(!loaded) {
            buildBase();
            while (enemies.size() < 2)
                spawnEnemy();
            loaded = true;
        }

        if(spawnedFood.size() < 10)
            spawnFood();



        Energy energyBase = (Energy)base.getComponent(Component.Type.ENERGY);
        if(spawnedPikdroids.size() < 100 && energyBase.isChargeFull()) {
            energyBase.discharge();
            buildPikdroid(((Pose)base.getComponent(Component.Type.POSE)).getCopy());
        }

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
        Entity pikdroid = new Entity();

        pose.translate(0,0,-0.2f);
        pikdroid.addComponent(pose);
        pikdroid.addComponent(new Visual(new float[] { 0.5f,  1.0f, 0.0f, 1.0f }));
        pikdroid.addComponent(new Movement(0.1f,8.0f));
        pikdroid.addComponent(new Energy(200,100,100));
        pikdroid.addComponent(new Intelligence(base));
        pikdroid.addComponent(new Detectable(DetectHint.PIKDROID));
        pikdroid.addComponent(new Detector());

        entityManager.add(pikdroid);
        spawnedPikdroids.put(pikdroid.getID(), pikdroid);
    }





    private void spawnFood() {
        Entity food = new Entity();

        Pose pose = new Pose();
        pose.translate(randomValue(20.0f),randomValue(20.0f),0.8f);

        Visual vis = new Visual(new float[] { 1.0f,  0.75f, 0.0f, 1.0f });
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

    private void buildBase() {
        base = new Entity();

        Pose pose = new Pose();
        pose.translate(0,0,0.9f);

        Detectable detectable = new Detectable(DetectHint.BASE);

        Energy energy = new Energy(1000,1000,800);

        Visual visual = new Visual(new float[] { 0.0f, 1.0f, 0.5f, 1.0f });
        visual.scale(2.0f, 2.0f, 1.0f);




        base.addComponent(pose);
        base.addComponent(detectable);
        base.addComponent(visual);
        base.addComponent(energy);

        entityManager.add(base);
    }

    private void spawnEnemy() {
        final Entity enemy = new Entity();

        final Pose pose = new Pose();
        pose.translate(0,13,0.8f);

        Detectable detectable = new Detectable(DetectHint.ENEMY);
        final Detector detector = new Detector();

        final Visual visual = new Visual(new float[] { 1.0f, 0.0f, 0.25f, 1.0f });
        visual.scale(1.0f, 1.0f, 1.0f);

        final Movement move = new Movement(0.1f,8.0f);
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

        final EntityCode findTarget = new EntityCode() {
            @Override
            public void execute() {
                Entity[] detections = detector.getDetections();
                if(detections[DetectHint.FOOD.ordinal()] != null) {
                    move.setTarget(detections[DetectHint.FOOD.ordinal()]);
                } else if (detections[DetectHint.PIKDROID.ordinal()] != null) {
                    move.setTarget(detections[DetectHint.PIKDROID.ordinal()]);
                } else {
                    modifyRandom(randomPose, 20.0f);
                    move.setTarget(randomTarget);

                }

            }
        };

        enemy.addTopicCode(Topic.MOVE_TARGET_REACHED, new EntityCode() {
            @Override
            public void execute() {
                if(move.getTarget().hasComponent(Component.Type.ENERGY)) {
                    Energy tEnergy = (Energy)move.getTarget().getComponent(Component.Type.ENERGY);
                    energy.charge(tEnergy.discharge());
                    energy.discharge(100);
                    entityManager.delete(move.getTarget());
                }
                findTarget.execute();
            }
        });

        enemy.addTopicCode(Topic.NEW_POSE_SECTOR_REACHED, findTarget);



        entityManager.add(enemy);
        enemies.put(enemy.getID(),enemy);
    }


    private void modifyRandom(Pose pose, float range) {
        pose.setPositionX(randomValue(range));
        pose.setPositionY(randomValue(range));
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
