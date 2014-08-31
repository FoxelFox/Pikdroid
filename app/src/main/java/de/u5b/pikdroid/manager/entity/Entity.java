package de.u5b.pikdroid.manager.entity;

import java.net.NetPermission;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import de.u5b.pikdroid.component.Component;

/**
 * Created by Foxel on 19.08.2014.
 */
public class Entity {
    private int id;
    private boolean isFinalized;
    //private ArrayList<Component> components;
    private Component[] components;

    /**
     * Only the EntityManager should create Entities!
     */
    public Entity(){
        this.id = -1;
        components = new Component[Component.Type.values().length];
        isFinalized = false;

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

    public int getID() {
        return id;
    }
}
