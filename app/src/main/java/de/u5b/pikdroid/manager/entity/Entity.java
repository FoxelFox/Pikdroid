package de.u5b.pikdroid.manager.entity;

import java.util.HashMap;

import de.u5b.pikdroid.component.Component;
import de.u5b.pikdroid.manager.event.EventTopic;

/**
 * The Main Entity for all Object in the Game.
 * Created by Foxel on 19.08.2014.
 */
public class Entity {
    private int id;
    private boolean isFinalized;
    private HashMap<EventTopic, EntityCode> eventCode;
    private Component[] components;

    /**
     * Create a new unregistered Entity that has currently no ID.
     * The EventManager will finalize the Entity with an ID.
     */
    public Entity(){
        this.id = -1;
        components = new Component[Component.Type.values().length];
        isFinalized = false;
        eventCode = new HashMap<EventTopic, EntityCode>();

    }

    /**
     * This Method should only be called from EntityManager
     * @param id unique EntityID
     */
    public void setId(int id) {
        this.id = id;
        isFinalized = true;
    }

    /**
     * Add Code that will be executed when an Event was thrown in relation with this Entity.
     * @param eventTopic Topic for Event
     * @param code Code to execute
     */
    public void addCodeForEvent(EventTopic eventTopic, EntityCode code) {
        eventCode.put(eventTopic, code);
    }

    /**
     * Notify the Entity that a Event was thrown in relation with it.
     * If the Entity has Code for this Event then it will be executed.
     * @param eventTopic Topic for Event
     */
    public void notify (EventTopic eventTopic) {
        if(eventCode.containsKey(eventTopic))
            eventCode.get(eventTopic).execute();
    }

    public boolean addComponent(Component component) {
        if(!isFinalized) {
            component.setEntity(this);
            components[component.getType().ordinal()] = component;
            return true;
        }
        return false;
    }

    /**
     * Returns the component of type @type
     * @return The Component of Type @type
     */
    public Component getComponent(Component.Type cType) {
        return components[cType.ordinal()];
    }

    public boolean hasComponent(Component.Type cType) {
        return components[cType.ordinal()] != null;
    }

    public int getID() {
        return id;
    }
}
