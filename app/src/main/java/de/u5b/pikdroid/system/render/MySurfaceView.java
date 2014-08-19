package de.u5b.pikdroid.system.render;

import android.content.Context;
import android.opengl.GLSurfaceView;

import de.u5b.pikdroid.game.Engine;

/**
 * The OpenGLSurface on Android Devices
 * Created by Foxel on 17.08.2014.
 */
public class MySurfaceView extends GLSurfaceView {


    public MySurfaceView(Context context, Engine engine) {
        super(context);
        // set OpenGLES 2.0 usage
        setEGLContextClientVersion(2);
        // set the render
        setRenderer(new RenderSystem(engine));

    }
}
