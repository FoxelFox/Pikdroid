package de.u5b.pikdroid.system.pikdroid;

import android.graphics.Matrix;

import java.util.Vector;

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

    public PikdroidSystem(Engine engine) {
        super(engine);

        // subscribe to Spawn new Pikdroids
        eventManager.subscribe(Topic.SPAWN_PIKDROID, this);

        pikdroids = new Vector<Entity>();
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getTopic()) {
            case SPAWN_PIKDROID: onSpawnPikdroid(event); break;
        }
    }

    private void onSpawnPikdroid(Event event) {
        pikdroids.add(buildPikdroid(0.0f, 0.0f));
    }

    /**
     * Build a new Pikdroid
     * @param x position X
     * @param y position Y
     * @return a new Pikdroid Entity
     */
    private Entity buildPikdroid(float x, float y) {
        Entity pikdroid = new Entity();

        Matrix matrix = new Matrix();
        matrix.setTranslate(x,y);

        pikdroid.addComponent(new Pose(matrix));
        pikdroid.addComponent(new Visual());
        pikdroid.addComponent(new Movement(1.0f,1.0f));
        pikdroid.addComponent(new Intelligence(4));

        entityManager.add(pikdroid);

        return  pikdroid;
    }
}
