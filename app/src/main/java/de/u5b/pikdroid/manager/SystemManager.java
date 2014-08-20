package de.u5b.pikdroid.manager;

import android.opengl.GLSurfaceView;

import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.system.IntelligenceSystem;
import de.u5b.pikdroid.system.pikdroid.PikdroidSystem;
import de.u5b.pikdroid.system.render.RenderSystem;

/**
 * Created by Foxel on 18.08.2014.
 */
public class SystemManager extends AManager {
    PikdroidSystem pikdroidSystem;
    RenderSystem renderSystem;
    IntelligenceSystem intelligenceSystem;

    public SystemManager(Engine engine){
        super(engine);

    }

    public void startGame() {
        pikdroidSystem = new PikdroidSystem(engine);
        renderSystem = new RenderSystem(engine);
        intelligenceSystem = new IntelligenceSystem(engine);
    }

}
