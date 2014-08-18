package de.u5b.pikdroid.game;

import de.u5b.pikdroid.manager.EntityManager;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.EventManager;
import de.u5b.pikdroid.manager.SystemManager;
import de.u5b.pikdroid.manager.event.Topic;

/**
 * Created by Foxel on 14.08.2014.
 */
public class Engine {
    private EntityManager entityManager;
    private EventManager eventManager;
    private SystemManager systemManager;

    private Engine engine;

    public Engine() {
        entityManager = new EntityManager(this);
        eventManager = new EventManager(this);
        systemManager = new SystemManager(this);
        eventManager.publish(new Event(Topic.SPAWN_PIKDROID,-1));
    }

    public void play() {
        systemManager.startGame();
    }

    public EntityManager getEntityManager(){
        return entityManager;
    }

    public SystemManager getSystemManager() {
        return systemManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

}
