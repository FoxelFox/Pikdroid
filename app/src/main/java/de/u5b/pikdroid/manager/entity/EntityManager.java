package de.u5b.pikdroid.manager.entity;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Stack;

import de.u5b.pikdroid.component.Component;
import de.u5b.pikdroid.component.Pose;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.AManager;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.Topic;

/**
 * The EntityManager is the Database for Collections of Components that form Entities.
 * Created by Foxel on 14.08.2014.
 */
public class EntityManager extends AManager {
    private ArrayList<Entity> entities;
    private Stack<Integer> eStack;

    public EntityManager(Engine engine) {
        super(engine);
        entities = new ArrayList<Entity>();
        eStack = new Stack<Integer>();
    }

    /**
     * Create a new Entity with @components
     * @return EntityID
     */
    public void add(Entity entity) {
        int index;
        if(eStack.empty()) {
            index = entities.size();
            entities.add(entity);
        } else {
            index = eStack.pop();
            entities.set(index, entity);
        }
        entity.setId(index);
        engine.getEventManager().publish(new Event(Topic.ENTITY_CREATED, entity));
    }

    /**
     * Delete the Entity from Database
     * @param entity Entity to delete
     */
    public void delete(Entity entity) {
        entities.set(entity.getID(), null);
        eStack.push(entity.getID());
    }
}
