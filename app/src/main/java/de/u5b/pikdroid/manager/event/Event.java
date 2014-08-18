package de.u5b.pikdroid.manager.event;

/**
 * Created by Foxel on 18.08.2014.
 */
public class Event {
    private Topic topic;
    private int entityID;

    public Event(Topic topic, int entityID) {
        this.topic = topic;
        this.entityID = entityID;
    }

    public Topic getTopic() {
        return topic;
    }

    public int getEntityID() {
        return entityID;
    }
}
