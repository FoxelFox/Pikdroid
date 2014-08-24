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
    private ArrayList<Component> components;

    /**
     * Only the EntityManager should create Entities!
     */
    public Entity(){
        this.id = -1;
        components = new ArrayList<Component>();
        isFinalized = false;
    }

    public boolean addComponent(Component component) {
        if(!isFinalized) {
            component.setEntity(this);
            components.add(component);
            return true;
        }
        return false;
    }



    /**
     * Returns the component of type @type
     * @param type ComponentType
     * @param <T>
     * @return The Component of Type @type
     */
    public <T extends Component> T getComponent(Class<T> type) {
        for(int i = 0; i < components.size(); ++i) {
            if(type.isInstance(components.get(i))) {
                return type.cast(components.get(i));
            }
        }
        //throw new NoSuchElementException("This Entity has no such Component!" + type.getClass().toString());
        return null;
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
