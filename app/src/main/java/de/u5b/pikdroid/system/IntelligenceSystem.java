package de.u5b.pikdroid.system;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import de.u5b.pikdroid.component.Energy;
import de.u5b.pikdroid.component.Intelligence;
import de.u5b.pikdroid.component.Pose;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.entity.Entity;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.Topic;
import de.u5b.pikdroid.system.render.object.ARenderObject;

/**
 * The IntelligenceSystem will try to control Entities that have an Intelligence Component
 * Created by Foxel on 19.08.2014.
 */
public class IntelligenceSystem extends ASystem{

    TreeMap<Integer, Entity> entities;    // Intelligence to control
    TreeMap<Integer, Entity> food;        // Food to collect

    public IntelligenceSystem(Engine engine){
        super(engine);

        eventManager.subscribe(Topic.ENTITY_CREATED, this);
        eventManager.subscribe(Topic.ENTITY_DELETED, this);

        entities = new TreeMap<Integer, Entity>();
        food = new TreeMap<Integer, Entity>();
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getTopic()) {
            case ENTITY_CREATED: onEntityCreated(event); break;
            case ENTITY_DELETED: onEntityDeleted(event); break;
        }
    }

    @Override
    public void update() {
        for (Map.Entry<Integer, Entity> entityEntry : entities.entrySet()) {

            Pose iPoseAi = entityEntry.getValue().getComponent(Pose.class);
            float minDistanceToFood = 1000.0f;
            Pose iBestMatch = null;

            // find shortest way to food
            for (Map.Entry<Integer, Entity> foodEntry : food.entrySet()) {

                Pose kPoseFood = foodEntry.getValue().getComponent(Pose.class);
                float kDistToFood = iPoseAi.distance(kPoseFood);
                if(minDistanceToFood > kDistToFood) {
                    minDistanceToFood = kDistToFood;
                    iBestMatch = kPoseFood;
                }
            }

            if(iBestMatch != null) {

                if(minDistanceToFood < 0.2f) {
                    // eat the food ...
                    entityManager.delete(iBestMatch.getEntity());
                }

                // look to food
                if(iPoseAi.dotForward(iBestMatch) < 0.1) {
                    iPoseAi.rotate(-8.0f, 0, 0, 1);
                } else {
                    iPoseAi.rotate(8.0f, 0, 0, 1);
                }

                // calc speed to move
                float speed = 0.25f * (float)Math.pow(minDistanceToFood,2);
                if(speed > 0.1f)
                    speed = 0.1f;
                iPoseAi.translate(speed , 0, 0);
            }
        }
    }

    private void onEntityCreated(Event event) {
        if(event.getEntity().getComponent(Intelligence.class) != null) {
            // This Entity has Intelligence to update
            entities.put(event.getEntity().getID(), event.getEntity());
        } else if(event.getEntity().getComponent(Energy.class) != null) {
            // This Entity has Energy its Food for me
            food.put(event.getEntity().getID(), event.getEntity());
        }
    }

    private void  onEntityDeleted(Event event) {
        entities.remove(event.getEntity().getID());
        food.remove(event.getEntity().getID());
    }
}
