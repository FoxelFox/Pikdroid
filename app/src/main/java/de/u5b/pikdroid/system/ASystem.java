package de.u5b.pikdroid.system;

import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.EntityManager;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.EventManager;
import de.u5b.pikdroid.manager.event.Topic;

/**
 * Created by Foxel on 13.08.2014.
 */
public abstract class ASystem {

    protected EntityManager entityManager;
    protected EventManager eventManager;

    public ASystem(Engine engine) {
        entityManager = engine.getEntityManager();
        eventManager = engine.getEventManager();
    }

    public abstract void handleEvent(Event event);
}
