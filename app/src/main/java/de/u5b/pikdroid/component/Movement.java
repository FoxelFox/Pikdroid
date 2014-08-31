package de.u5b.pikdroid.component;

import de.u5b.pikdroid.manager.entity.Entity;

/**
 * The Movement component contains parameter to move the Entity
 * Created by Foxel on 19.08.2014.
 */
public class Movement extends Component {
    private float linearSpeed;      // max linear speed
    private float angularSpeed;     // max angular speed

    private Entity target;
    private boolean hasTarget;
    private float distanceToReach;

    private boolean randomOnNoTarget;

    public Movement(float linearSpeed, float angularSpeed) {
        this.linearSpeed = linearSpeed;
        this.angularSpeed = angularSpeed;
        randomOnNoTarget = true;
        distanceToReach = 0.2f;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
        if(target == null)
            hasTarget = false;
        else
            hasTarget = true;
    }

    public boolean hasTarget() {
        return hasTarget;
    }

    public float getLinearSpeed() {
        return linearSpeed;
    }

    public void setLinearSpeed(float linearSpeed) {
        this.linearSpeed = linearSpeed;
    }

    public float getAngularSpeed() {
        return angularSpeed;
    }

    public void setAngularSpeed(float angularSpeed) {
        this.angularSpeed = angularSpeed;
    }

    public boolean isRandomOnNoTarget() {
        return randomOnNoTarget;
    }

    public void setRandomOnNoTarget(boolean randomOnNoTarget) {
        this.randomOnNoTarget = randomOnNoTarget;
    }

    public float getDistanceToReach() {
        return distanceToReach;
    }

    public void setDistanceToReach(float distanceToReach) {
        this.distanceToReach = distanceToReach;
    }

    @Override
    public Type getType() {
        return Type.MOVEMENT;
    }
}
