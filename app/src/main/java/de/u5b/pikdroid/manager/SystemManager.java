package de.u5b.pikdroid.manager;

import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.system.pikdroid.PikdroidSystem;

/**
 * Created by Foxel on 18.08.2014.
 */
public class SystemManager extends AManager {
    PikdroidSystem pikdroidSystem;

    public SystemManager(Engine engine){
        super(engine);

    }

    public void startGame() {
        pikdroidSystem = new PikdroidSystem(engine);


    }

}
