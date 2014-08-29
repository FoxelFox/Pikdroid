package de.u5b.pikdroid.system.pikdroid;

import android.graphics.Matrix;

import java.util.TreeMap;
import java.util.Vector;

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
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.Topic;
import de.u5b.pikdroid.system.ASystem;

/**
 * The Pikdroid Game System
 * Created by Foxel on 18.08.2014.
 */
public class PikdroidSystem extends ASystem {

    private TreeMap<Integer, Entity> spawnedPikdroids;
    private TreeMap<Integer, Entity> spawnedFood;
    private Entity base;


    public PikdroidSystem(Engine engine) {
        super(engine);

        // subscribe to Spawn new Pikdroids
        eventManager.subscribe(Topic.SPAWN_PIKDROID, this);
        eventManager.subscribe(Topic.ENTITY_DELETED, this);

        spawnedPikdroids = new TreeMap<Integer, Entity>();
        spawnedFood = new TreeMap<Integer, Entity>();
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getTopic()) {
            case SPAWN_PIKDROID: onSpawnPikdroid(event); break;
            case ENTITY_DELETED: onEntityDeleted(event); break;

        }
    }

    public void update() {
        if(base == null) {
            buildBase();
        }

        if(spawnedFood.size() < 10)
            spawnFood();
    }

    private void onEntityDeleted(Event event) {
        spawnedFood.remove(event.getEntity().getID());
        spawnedPikdroids.remove(event.getEntity().getID());
    }

    private void onSpawnPikdroid(Event event) {
        buildPikdroid(event.getEntity().getComponent(Pose.class));
    }

    /**
     * Build a new Pikdroid
     * @param pose position
     * @return a new Pikdroid Entity
     */
    private void buildPikdroid(Pose pose) {
        Entity pikdroid = new Entity();

        pikdroid.addComponent(pose);
        pikdroid.addComponent(new Visual(new float[] { 0.5f,  1.0f, 0.0f, 1.0f }));
        pikdroid.addComponent(new Movement(1.0f,1.0f));
        pikdroid.addComponent(new Intelligence(base));
        pikdroid.addComponent(new Detectable(DetectHint.PIKDROID));
        pikdroid.addComponent(new Detector());

        entityManager.add(pikdroid);
        spawnedPikdroids.put(pikdroid.getID(), pikdroid);
    }

    private void spawnFood() {
        Entity food = new Entity();

        Pose pose = new Pose();
        pose.translate(randomValue(20.0f),randomValue(20.0f),0);

        Visual vis = new Visual(new float[] { 0.0f,  0.5f, 1.0f, 1.0f });
        vis.scale(0.5f,0.5f,1.0f);

        Energy energy = new Energy(100,100);
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
        pose.translate(8,12,0);

        Detectable detectable = new Detectable(DetectHint.BASE);

        Visual visual = new Visual(new float[] { 0.0f, 1.0f, 0.5f, 1.0f });
        visual.scale(2.0f, 2.0f, 1.0f);


        base.addComponent(pose);
        base.addComponent(detectable);
        base.addComponent(visual);

        entityManager.add(base);
    }

    private float randomValue(float range){
        return ((float)Math.random() - 0.5f) * range;
    }
}
