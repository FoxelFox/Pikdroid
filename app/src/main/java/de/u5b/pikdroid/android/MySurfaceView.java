package de.u5b.pikdroid.android;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import de.u5b.pikdroid.system.input.InputSystem;

/**
 * The OpenGLSurface on Android Devices
 * Created by Foxel on 17.08.2014.
 */
public class MySurfaceView extends GLSurfaceView {

    private InputSystem inputSystem;
    private float lastX, lastY;

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
        y = y * (-1.0f) * (float)getHeight() / (float)getWidth();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                inputSystem.onPressed(x, y);
                break;
            case MotionEvent.ACTION_UP:
                inputSystem.onReleased(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                inputSystem.onMoved(x - lastX, y - lastY);
                break;
        }

        lastX = x;
        lastY = y;

        return true;
    }


}
