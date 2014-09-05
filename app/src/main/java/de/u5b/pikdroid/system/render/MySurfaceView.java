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

/**
 * The OpenGLSurface on Android Devices
 * Created by Foxel on 17.08.2014.
 */
public class MySurfaceView extends GLSurfaceView {

    private Engine engine;

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

    public void setEngine(Engine engine) {
        setRenderer(new RenderSystem(engine));
        this.engine = engine;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX() / (float)getWidth() * 2.0f - 1.0f;
        float y = event.getY() / (float)getHeight() * 2.0f - 1.0f;
        y = y * (-1.0f);

        x *= (float)getWidth()*0.02f;
        y *= (float)getHeight()*0.02f;


        System.out.println("x: " + x + "y: " + y);

        float[] matrix = new float[16];
        Matrix.setIdentityM(matrix,0);


        Matrix.translateM(matrix,0,x ,y, 0.0f);


        Entity entity = new Entity();
        entity.addComponent(new Pose(matrix));

        // async queued event
        engine.getEventManager().publishQueued(new Event(EventTopic.SET_USER_TARGET, entity));
        return true;
    }

}
