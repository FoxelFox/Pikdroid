package de.u5b.pikdroid.system.pikdroid;

import android.graphics.Matrix;

import java.util.ArrayList;
import java.util.Vector;

import de.u5b.pikdroid.component.Component;
import de.u5b.pikdroid.component.Intelligence;
import de.u5b.pikdroid.component.Movement;
import de.u5b.pikdroid.component.Pose;
import de.u5b.pikdroid.component.Visual;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.Topic;
import de.u5b.pikdroid.system.ASystem;

/**
 * The Pikdroid Game System
 * Created by Foxel on 18.08.2014.
 */
public class PikdroidSystem extends ASystem {

    Vector<Integer> pikdroids;

    public PikdroidSystem(Engine engine) {
        super(engine);

        // subscribe to Spawn new Pikdroids
        eventManager.subscribe(Topic.SPAWN_PIKDROID, this);

        pikdroids = new Vector<Integer>();
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getTopic()) {
            case SPAWN_PIKDROID: pikdroids.add(entityManager.create(buildPikdroid(0.0f, 0.0f)));
        }
    }

    /**
     * Build a new Pikdroid
     * @param x position X
     * @param y position >
     * @return a new Pikdroid Entity
     */
    private ArrayList<Component> buildPikdroid(float x, float y) {

        Matrix matrix = new Matrix();
        matrix.setTranslate(x,y);

        ArrayList<Component> pikdroid = new ArrayList<Component>();
        pikdroid.add(new Pose(matrix));
        pikdroid.add(new Visual());
        pikdroid.add(new Movement(1.0f,1.0f));
        pikdroid.add(new Intelligence(4));

        return  pikdroid;
    }
}
