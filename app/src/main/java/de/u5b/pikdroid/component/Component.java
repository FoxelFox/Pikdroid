package de.u5b.pikdroid.component;

import de.u5b.pikdroid.manager.entity.Entity;

/**
 * The Abstract Component Class for all Components
 * Created by Foxel on 13.08.2014.
 */
public abstract class Component {
    protected Entity entity;

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
