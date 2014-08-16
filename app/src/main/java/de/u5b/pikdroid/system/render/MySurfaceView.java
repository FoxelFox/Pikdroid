package de.u5b.pikdroid.system.render;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Foxel on 17.08.2014.
 */
public class MySurfaceView extends GLSurfaceView {


    public MySurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        setRenderer(new RenderSystem());

    }
}
