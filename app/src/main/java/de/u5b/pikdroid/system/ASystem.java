package de.u5b.pikdroid.system;

import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.entity.EntityManager;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.EventManager;

/**
 * Abstract System Class for all Systems
 * Created by Foxel on 13.08.2014.
 */
public abstract class ASystem {

    protected EntityManager entityManager;
    protected EventManager eventManager;
    protected Engine engine;

    public ASystem(Engine engine) {
        entityManager = engine.getEntityManager();
        eventManager = engine.getEventManager();
        this.engine = engine;
    }

    public abstract void handleEvent(Event event);

    public abstract void update();
}
