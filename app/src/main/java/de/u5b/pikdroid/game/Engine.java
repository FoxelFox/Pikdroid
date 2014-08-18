package de.u5b.pikdroid.game;

import de.u5b.pikdroid.manager.EntityManager;
import de.u5b.pikdroid.manager.EventManager;
import de.u5b.pikdroid.manager.GameManager;

/**
 * Created by Foxel on 14.08.2014.
 */
public class Engine {
    private EntityManager entityManager;
    private EventManager eventManager;
    private GameManager gameManager;

    private Engine engine;

    private Engine() {
        entityManager = new EntityManager(this);
        eventManager = new EventManager(this);
        gameManager = new GameManager(this);
    }

    public EntityManager getEntityManager(){
        return entityManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }
}
