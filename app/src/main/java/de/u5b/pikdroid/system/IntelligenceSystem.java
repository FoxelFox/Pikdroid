package de.u5b.pikdroid.system;

import android.graphics.Matrix;

import java.util.NoSuchElementException;
import java.util.Vector;

import de.u5b.pikdroid.component.Energy;
import de.u5b.pikdroid.component.Intelligence;
import de.u5b.pikdroid.component.Pose;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.entity.Entity;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.Topic;

/**
 * The IntelligenceSystem will try to control Entities that have an Intelligence Component
 * Created by Foxel on 19.08.2014.
 */
public class IntelligenceSystem extends ASystem{

    Vector<Entity> entities;    // Intelligence to control
    Vector<Entity> food;        // Food to collect

    public IntelligenceSystem(Engine engine){
        super(engine);
        eventManager.subscribe(Topic.UPDATE_INTELLIGENCE, this);
        eventManager.subscribe(Topic.ENTITY_CREATED, this);
        entities = new Vector<Entity>();
        food = new Vector<Entity>();
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getTopic()) {
            case UPDATE_INTELLIGENCE: onUpdateIntelligence(); break;
            case ENTITY_CREATED: onEntityCreated(event); break;
        }
    }

    private void onEntityCreated(Event event) {
        try {
            if(event.getEntity().getComponent(Intelligence.class) != null) {
                // This Entity has Intelligence to update
                entities.add(event.getEntity());
            } else if(event.getEntity().getComponent(Energy.class) != null) {
                // This Entity has Energy its Food for me
                food.add(event.getEntity());
            }
        } catch (NoSuchElementException e) {
            // This Entity has no such component
            return;
        }
    }

    private void onUpdateIntelligence() {
        for(int i = 0; i < entities.size(); ++i) {
            Pose pose = entities.get(i).getComponent(Pose.class);
            pose.translate(((float)Math.random() - 0.5f) * 0.25f,((float)Math.random() - 0.5f) * 0.25f,0);
        }
    }
}
