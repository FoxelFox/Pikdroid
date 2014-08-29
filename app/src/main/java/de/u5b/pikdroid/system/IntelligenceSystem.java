package de.u5b.pikdroid.system;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import de.u5b.pikdroid.component.Energy;
import de.u5b.pikdroid.component.Intelligence;
import de.u5b.pikdroid.component.Movement;
import de.u5b.pikdroid.component.Pose;
import de.u5b.pikdroid.component.detect.DetectEntry;
import de.u5b.pikdroid.component.detect.DetectHint;
import de.u5b.pikdroid.component.detect.Detector;
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
        eventManager.subscribe(Topic.MOVE_TARGET_REACHED, this);

        entities = new TreeMap<Integer, Entity>();
        food = new TreeMap<Integer, Entity>();
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getTopic()) {
            case ENTITY_CREATED: onEntityCreated(event); break;
            case ENTITY_DELETED: onEntityDeleted(event); break;
            case MOVE_TARGET_REACHED: onMoveTargetReached(event); break;
        }
    }

    @Override
    public void update() {
        for (Map.Entry<Integer, Entity> entityEntry : entities.entrySet()) {
            Entity entity = entityEntry.getValue();
            Detector detector = entity.getComponent(Detector.class);
            Pose poseAi = entity.getComponent(Pose.class);
            Intelligence intelligence = entity.getComponent(Intelligence.class);
            Movement movement = entity.getComponent(Movement.class);


            if(!intelligence.hasFood()) {

                DetectEntry food = detector.getMinDistanceDetection(DetectHint.FOOD);

                if (food != null) {
                    movement.setTarget(food.getEntity());
                }
            }

            // edge Detect
            if(poseAi.getPositionX() < -10.0f || poseAi.getPositionX() > 10.0f)
                poseAi.rotate(90.f,0,0,1);
            if(poseAi.getPositionY() < -13.0f || poseAi.getPositionY() > 13.0f)
                poseAi.rotate(90.f,0,0,1);

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

    private void onMoveTargetReached(Event event) {
        Intelligence intelligence = event.getEntity().getComponent(Intelligence.class);
        Movement movement = event.getEntity().getComponent(Movement.class);
        if(intelligence != null) {
            if(intelligence.hasFood()) {
                intelligence.setHasFood(false);
                movement.setTarget(null);
            } else {
                if(entityManager.delete(movement.getTarget())) {
                    intelligence.setHasFood(true);
                    movement.setTarget(intelligence.getBase());
                } else {
                    movement.setTarget(null);
                }
            }
        }
    }
}
