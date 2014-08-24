package de.u5b.pikdroid.game;

import de.u5b.pikdroid.manager.entity.EntityManager;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.EventManager;
import de.u5b.pikdroid.manager.SystemManager;
import de.u5b.pikdroid.manager.event.Topic;

/**
 * This is the main class for the Game. It contains all Manager and starts the game.
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
    }

    /**
     * Start the Game
     */
    public void play() {
        systemManager.startGame();
    }

    public void update() {
        eventManager.clearQueue();

        eventManager.publish(new Event(Topic.UPDATE_PIKDROID,null));
        eventManager.publish(new Event(Topic.UPDATE_INTELLIGENCE,null));
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
