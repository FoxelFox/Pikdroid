package de.u5b.pikdroid.component.detect;

import de.u5b.pikdroid.manager.entity.Entity;

/**
 * Created by Foxel on 26.08.2014.
 */
public class DetectEntry {
    private Entity entity;
    private float distance;

    public DetectEntry(Entity entity, float distance) {
        this.entity = entity;
        this.distance = distance;
    }


    public Entity getEntity() {
        return entity;
    }

    public float getDistance() {
        return distance;
    }
}
