package de.u5b.pikdroid.game;

import de.u5b.pikdroid.manager.EntityManager;
import de.u5b.pikdroid.manager.EventManager;
import de.u5b.pikdroid.manager.SystemManager;

/**
 * Created by Foxel on 14.08.2014.
 */
public class Engine {
    private EntityManager entityManager;
    private EventManager eventManager;
    private SystemManager gameManager;

    private Engine engine;

    private Engine() {
        entityManager = new EntityManager(this);
        eventManager = new EventManager(this);
        gameManager = new SystemManager(this);
    }

    public void play() {
        gameManager.startGame();
    }

    public EntityManager getEntityManager(){
        return entityManager;
    }

    public SystemManager getGameManager() {
        return gameManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }
}
