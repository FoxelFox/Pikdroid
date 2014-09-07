package de.u5b.pikdroid.manager;

import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.system.detect.DetectSystem;
import de.u5b.pikdroid.system.EnergySystem;
import de.u5b.pikdroid.system.HintSystem;
import de.u5b.pikdroid.system.MovementSystem;
import de.u5b.pikdroid.system.pikdroid.PikdroidSystem;
import de.u5b.pikdroid.system.render.RenderSystem;

/**
 * Created by Foxel on 18.08.2014.
 */
public class SystemManager extends AManager {
    PikdroidSystem pikdroidSystem;
    RenderSystem renderSystem;
    DetectSystem detectSystem;
    MovementSystem movementSystem;
    EnergySystem energySystem;
    HintSystem hintSystem;

    public SystemManager(Engine engine){
        super(engine);

    }

    public void startGame() {
        pikdroidSystem = new PikdroidSystem(engine);
        renderSystem = new RenderSystem(engine);
        detectSystem = new DetectSystem(engine);
        movementSystem = new MovementSystem(engine);
        energySystem = new EnergySystem(engine);
        hintSystem = new HintSystem(engine);

    }

    public void update() {
        hintSystem.update();
        detectSystem.update();
        energySystem.update();
        movementSystem.update();
        pikdroidSystem.update();
        renderSystem.update();

    }

}
