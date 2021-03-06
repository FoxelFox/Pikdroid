package de.u5b.pikdroid.system.move;

import java.util.ArrayList;

import de.u5b.pikdroid.component.Component;
import de.u5b.pikdroid.component.Movement;
import de.u5b.pikdroid.component.Pose;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.entity.Entity;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.EventTopic;
import de.u5b.pikdroid.system.ASystem;

/**
 * The MoveSystem will try to move Entities to his targets.
 * Created by Foxel on 29.08.2014.
 */
public class MoveSystem extends ASystem {

    ArrayList<Entity> moveableList;

    public MoveSystem(Engine engine) {
        super(engine);
        moveableList = new ArrayList<Entity>();
        eventManager.subscribe(EventTopic.ENTITY_CREATED, this);
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getEventTopic()) {
            case ENTITY_CREATED: onEntityCreated(event); break;
            case ENTITY_DELETED: onEntityDeleted(event); break;
        }
    }

    @Override
    public void update() {
        Movement move;
        Pose pose;
        boolean poseSectorChanged = false;
        for (int i = 0; i < moveableList.size(); ++i) {
            pose = (Pose)moveableList.get(i).getComponent(Component.Type.POSE);
            move = (Movement)moveableList.get(i).getComponent(Component.Type.MOVEMENT);

            if (move.hasTarget()) {
                // the movement component has a target to reach

                Pose target = (Pose)move.getTarget().getComponent(Component.Type.POSE);

                if (pose.dotForward(target) < 0.1) {
                    pose.rotate(-move.getAngularSpeed(), 0, 0, 1);
                } else {
                    pose.rotate(move.getAngularSpeed(), 0, 0, 1);
                }

                // calc speed to move
                float distance = pose.distance(target);
                float speed = move.getDamper() * distance;
                if (speed > move.getLinearSpeed())
                    speed = move.getLinearSpeed();
                pose.translate(speed, 0, 0);

                if(distance < move.getDistanceToReach()) {
                    eventManager.publish(new Event(EventTopic.MOVE_TARGET_REACHED, moveableList.get(i)));
                }

            } else if (move.isRandomOnNoTarget()) {
                // no target is setup and the move component has set the flag to random move now

                if (Math.random() < 0.5) {
                    pose.rotate(-move.getAngularSpeed(), 0, 0, 1);
                } else {
                    pose.rotate(move.getAngularSpeed(), 0, 0, 1);
                }

                pose.translate(move.getLinearSpeed(), 0, 0);
            }
            // check if new sector was reached

            if(pose.isNewSectorReached()) {
                eventManager.publish(new Event(EventTopic.NEW_POSE_SECTOR_REACHED, pose.getEntity()));
                poseSectorChanged = true;
            }
        }
        if(poseSectorChanged) {
            eventManager.publish(new Event(EventTopic.POSE_SECTOR_CHANGED, null));
        }
    }

    private void onEntityCreated(Event event) {
        Movement move = (Movement)event.getEntity().getComponent(Component.Type.MOVEMENT);

        if (move != null)
            moveableList.add(event.getEntity());
    }

    private void onEntityDeleted(Event event) {
        moveableList.remove(event.getEntity());
    }
}
