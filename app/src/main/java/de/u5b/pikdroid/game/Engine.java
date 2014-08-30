package de.u5b.pikdroid.game;

import de.u5b.pikdroid.android.PikdroidActivity;
import de.u5b.pikdroid.manager.entity.EntityManager;
import de.u5b.pikdroid.manager.event.EventManager;
import de.u5b.pikdroid.manager.SystemManager;

/**
 * This is the main class for the Game. It contains all Manager and starts the game.
 * Created by Foxel on 14.08.2014.
 */
public class Engine {
    private boolean isRunning;
    private EntityManager entityManager;
    private EventManager eventManager;
    private SystemManager systemManager;

    private PikdroidActivity view;
    private int pikdroidCount;

    public Engine(PikdroidActivity view) {
        isRunning = false;
        entityManager = new EntityManager(this);
        eventManager = new EventManager(this);
        systemManager = new SystemManager(this);
        this.view = view;
    }

    /**
     * Start the Game
     */
    public void play() {
        systemManager.startGame();
        isRunning = true;
    }

    public void update() {
        if(isRunning) {
            eventManager.update();
            entityManager.update();
            systemManager.update();
            view.update();
        }
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

    public void setPikdroidCount(int count) {
        pikdroidCount = count;
    }

    public int getPikdroidCount() {
        return pikdroidCount;
    }
}
