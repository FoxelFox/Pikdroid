package de.u5b.pikdroid.manager;

import java.util.ArrayList;
import java.util.Stack;

import de.u5b.pikdroid.component.Component;

/**
 * Created by Foxel on 14.08.2014.
 */
public class EntityManager {
    private ArrayList<ArrayList<Component>> entities;
    private Stack<Integer> eStack;

    public EntityManager() {
        entities = new ArrayList<ArrayList<Component>>();
        eStack = new Stack<Integer>();
    }

    /**
     * Create a new Entity with @components
     * @return EntityID
     */
    public int create(ArrayList<Component> components) {
        if(eStack.empty()) {
            entities.add(components);
            return entities.size() -1;
        } else {
            int i = eStack.pop();
            entities.set(i, components);
            return i;
        }
    }

    /**
     * Delete the Entity with the @id from Database
     * @param id EntityID
     */
    public void delete(int id) {
        entities.set(id, null);
        eStack.push(id);
    }
}
