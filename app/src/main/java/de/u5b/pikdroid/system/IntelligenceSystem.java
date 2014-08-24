package de.u5b.pikdroid.system;

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

        if(event.getEntity().getComponent(Intelligence.class) != null) {
            // This Entity has Intelligence to update
            entities.add(event.getEntity());
        } else if(event.getEntity().getComponent(Energy.class) != null) {
            // This Entity has Energy its Food for me
            food.add(event.getEntity());
        }
    }

    private void onUpdateIntelligence() {

        for(int i = 0; i < entities.size(); ++i) {
            Pose iPoseAi = entities.get(i).getComponent(Pose.class);
            float minDistanceToFood = 1000.0f;
            Pose iBestMatch = null;

            // find shortest way to food
            for(int k = 0; k < food.size(); ++k) {
                Pose kPoseFood = food.get(k).getComponent(Pose.class);
                float kDistToFood = iPoseAi.distance(kPoseFood);
                if(minDistanceToFood > kDistToFood) {
                    minDistanceToFood = kDistToFood;
                    iBestMatch = kPoseFood;
                }
            }

            if(iBestMatch != null) {
                float[] dir = iPoseAi.nray(iBestMatch);
                if(iPoseAi.dotForward(iBestMatch) < 0.1) {
                    iPoseAi.rotate(-8.0f, 0, 0, 1);
                } else {
                    iPoseAi.rotate(8.0f, 0, 0, 1);

                }
                float speed = 0.01f * (float)Math.pow(minDistanceToFood,2);
                if(speed > 0.1f)
                    speed = 0.1f;
                iPoseAi.translate(speed , 0, 0);
            }
        }
    }
}
