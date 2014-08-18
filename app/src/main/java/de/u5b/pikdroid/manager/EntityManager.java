package de.u5b.pikdroid.manager;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Stack;

import de.u5b.pikdroid.component.Component;
import de.u5b.pikdroid.component.Pose;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.Topic;

/**
 * Created by Foxel on 14.08.2014.
 */
public class EntityManager extends AManager {
    private ArrayList<ArrayList<Component>> entities;
    private Stack<Integer> eStack;

    public EntityManager(Engine engine) {
        super(engine);
        entities = new ArrayList<ArrayList<Component>>();
        eStack = new Stack<Integer>();
    }

    /**
     * Create a new Entity with @components
     * @return EntityID
     */
    public int create(ArrayList<Component> components) {
        int index;
        if(eStack.empty()) {
            entities.add(components);
            index = entities.size() -1;
        } else {
            index = eStack.pop();
            entities.set(index, components);
        }
        engine.getEventManager().publish(new Event(Topic.ENTITY_CREATED, index));
        return index;
    }


    /**
     * Returns the component of type @type
     * @param id EntityID
     * @param type ComponentType
     * @param <T>
     * @return The Component of Type @type
     */
    public <T extends Component> T getComponent(int id, Class<T> type) {
        ArrayList<Component> e = entities.get(id);
        for(int i = 0; i < e.size(); ++i) {
            if(type.isInstance(e.get(i))) {
                return type.cast(e.get(i));
            }
        }
        throw new NoSuchElementException("This Entity has no such Component!" + type.getClass().toString());
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
