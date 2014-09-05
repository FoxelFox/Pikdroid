package de.u5b.pikdroid.manager.event;

import de.u5b.pikdroid.manager.entity.Entity;

/**
 * The Event class Contains an Topic and the EntityID
 * Created by Foxel on 18.08.2014.
 */
public class Event {
    private EventTopic eventTopic;        // EventTopic
    private Entity entity;      // Entity
    private Entity target;      // Target (eg. entity attack to target) usually not needed

    public Event(EventTopic eventTopic, Entity entity) {
        this.eventTopic = eventTopic;
        this.entity = entity;
        this.target = null;
    }

    public Event(EventTopic eventTopic, Entity entity, Entity target) {
        this.eventTopic = eventTopic;
        this.entity = entity;
        this.target = target;
    }



    public EventTopic getEventTopic() {
        return eventTopic;
    }

    public Entity getEntity() {
        return entity;
    }

    public Entity getTarget() {
        return target;
    }
}
