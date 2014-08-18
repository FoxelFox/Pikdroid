package de.u5b.pikdroid.manager;

import de.u5b.pikdroid.game.Engine;

/**
 * Created by Foxel on 18.08.2014.
 */
public abstract class Manager {
    private Engine engine;

    public Manager(Engine engine) {
        this.engine = engine;
    }
}
