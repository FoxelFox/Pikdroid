package game;

import manager.EntityManager;
import manager.EventManager;

/**
 * Created by Foxel on 14.08.2014.
 */
public class Engine {
    private EntityManager entityManager;
    private EventManager eventManager;

    public Engine() {
        entityManager = new EntityManager();
        eventManager = new EventManager();
    }
}
