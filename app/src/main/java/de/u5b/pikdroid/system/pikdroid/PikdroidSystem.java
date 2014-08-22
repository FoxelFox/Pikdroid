package de.u5b.pikdroid.system.pikdroid;

import android.graphics.Matrix;

import java.util.Vector;

import de.u5b.pikdroid.component.Energy;
import de.u5b.pikdroid.component.Intelligence;
import de.u5b.pikdroid.component.Movement;
import de.u5b.pikdroid.component.Pose;
import de.u5b.pikdroid.component.Visual;
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

    Vector<Entity> pikdroids;
    Vector<Entity> food;

    public PikdroidSystem(Engine engine) {
        super(engine);

        // subscribe to Spawn new Pikdroids
        eventManager.subscribe(Topic.SPAWN_PIKDROID, this);

        pikdroids = new Vector<Entity>();
        food = new Vector<Entity>();
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getTopic()) {
            case SPAWN_PIKDROID: onSpawnPikdroid(event); break;
        }
    }

    private void onSpawnPikdroid(Event event) {
        pikdroids.add(buildPikdroid(event.getEntity().getComponent(Pose.class)));

        // TODO: this should be called on an special event
        if(food.size() < 10) {
            food.add(spawnFood());
        }
    }

    /**
     * Build a new Pikdroid
     * @param pose position
     * @return a new Pikdroid Entity
     */
    private Entity buildPikdroid(Pose pose) {
        Entity pikdroid = new Entity();

        pikdroid.addComponent(pose);
        pikdroid.addComponent(new Visual(new float[] { 0.5f,  1.0f, 0.0f, 1.0f }));
        pikdroid.addComponent(new Movement(1.0f,1.0f));
        pikdroid.addComponent(new Intelligence(4));

        entityManager.add(pikdroid);

        return  pikdroid;
    }

    private Entity spawnFood() {
        Entity food = new Entity();

        Pose pose = new Pose();
        pose.translate(randomValue(20.0f),randomValue(20.0f),0);

        Visual vis = new Visual(new float[] { 0.0f,  0.5f, 1.0f, 1.0f });
        vis.scale(0.5f,0.5f,1.0f);

        Energy energy = new Energy(100,100);


        food.addComponent(pose);
        food.addComponent(vis);
        food.addComponent(energy);

        entityManager.add(food);
        return food;
    }

    private float randomValue(float range){
        return ((float)Math.random() - 0.5f) * range;
    }
}
