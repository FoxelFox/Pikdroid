package de.u5b.pikdroid.manager.event;

/**
 * The Event class Contains an Topic and the EntityID
 * Created by Foxel on 18.08.2014.
 */
public class Event {
    private Topic topic;    // EventTopic
    private int entityID;   // EntityID

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
