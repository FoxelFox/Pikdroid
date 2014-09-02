package de.u5b.pikdroid.manager.entity;

import java.util.HashMap;

import de.u5b.pikdroid.component.Component;
import de.u5b.pikdroid.manager.event.Topic;

/**
 * Created by Foxel on 19.08.2014.
 */
public class Entity {
    private int id;
    private boolean isFinalized;
    private HashMap<Topic, EntityCode> topicCode;
    private Component[] components;

    /**
     * Only the EntityManager should create Entities!
     */
    public Entity(){
        this.id = -1;
        components = new Component[Component.Type.values().length];
        isFinalized = false;
        topicCode = new HashMap<Topic, EntityCode>();

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

    /**
     * This Method should only be called from EntityManager
     * @param id unique EntityID
     */
    public void setId(int id) {
        this.id = id;
        isFinalized = true;
    }

    public void addTopicCode(Topic topic, EntityCode code) {
        topicCode.put(topic, code);
    }

    public void notify (Topic topic) {
        if(topicCode.containsKey(topic))
            topicCode.get(topic).execute();
    }

    public int getID() {
        return id;
    }
}
