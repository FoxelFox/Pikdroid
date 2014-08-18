package de.u5b.pikdroid.system;

import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.EntityManager;
import de.u5b.pikdroid.manager.EventManager;
import de.u5b.pikdroid.manager.EventTopic;

/**
 * Created by Foxel on 13.08.2014.
 */
public abstract class ISystem {

    protected EntityManager entityManager;
    protected EventManager eventManager;

    public ISystem(Engine engine) {
        entityManager = engine.getEntityManager();
        eventManager = engine.getEventManager();
    }

    public abstract void handleEvent(EventTopic eventTopic);
}
