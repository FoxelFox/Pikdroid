package de.u5b.pikdroid.system.render;

import android.content.Context;
import android.opengl.Matrix;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import de.u5b.pikdroid.component.Pose;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.entity.Entity;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.EventTopic;
import de.u5b.pikdroid.system.InputSystem;

/**
 * The OpenGLSurface on Android Devices
 * Created by Foxel on 17.08.2014.
 */
public class MySurfaceView extends GLSurfaceView {

    private InputSystem inputSystem;

    public MySurfaceView(Context context) {
        super(context);
        initGL(context);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGL(context);
    }

    private void initGL(Context context) {
        // set OpenGLES 2.0 usage
        setEGLContextClientVersion(2);
        setEGLConfigChooser(8 , 8, 8, 8, 16, 0);
    }

    public void setInputHandler(InputSystem inputHandler) {
        inputSystem = inputHandler;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX() / (float)getWidth() * 2.0f - 1.0f;
        float y = event.getY() / (float)getHeight() * 2.0f - 1.0f;
        y = y * (-1.0f);
        y *= (float)getHeight() / (float)getWidth();

        inputSystem.onPressed(x,y);
        return true;
    }


}
