package de.u5b.pikdroid.manager;

import java.util.ArrayList;

import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.system.ASystem;
import de.u5b.pikdroid.system.InputSystem;
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
    private ArrayList<ASystem> systems;

    public SystemManager(Engine engine){
        super(engine);
        systems = new ArrayList<ASystem>(16);
    }

    public void startGame() {
        systems.add(new InputSystem(engine));
        systems.add(new HintSystem(engine));
        systems.add(new DetectSystem(engine));
        systems.add(new EnergySystem(engine));
        systems.add(new MovementSystem(engine));
        systems.add(new PikdroidSystem(engine));
        systems.add(new RenderSystem(engine));
    }

    public void update() {
        for (ASystem system : systems) {
            system.update();
        }
    }

    /**
     * Returns the System
     * @param type Type of the System
     * @param <T>
     * @return
     */
    public <T extends ASystem> T getSystem(Class<T> type) {
        for (ASystem system : systems) {
            if(type.isInstance(system)) {
                return type.cast(system);
            }
        }
        return null;
    }
}
