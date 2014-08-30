package de.u5b.pikdroid.manager.event;

import de.u5b.pikdroid.manager.entity.Entity;

/**
 * The Event class Contains an Topic and the EntityID
 * Created by Foxel on 18.08.2014.
 */
public class Event {
    private Topic topic;        // EventTopic
    private Entity entity;      // Entity
    private Entity target;      // Target (eg. entity attack to target) usually not needed

    public Event(Topic topic, Entity entity) {
        this.topic = topic;
        this.entity = entity;
        this.target = null;
    }

    public Event(Topic topic, Entity entity, Entity target) {
        this.topic = topic;
        this.entity = entity;
        this.target = target;
    }



    public Topic getTopic() {
        return topic;
    }

    public Entity getEntity() {
        return entity;
    }

    public Entity getTarget() {
        return target;
    }
}
