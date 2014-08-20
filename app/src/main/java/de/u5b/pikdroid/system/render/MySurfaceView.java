package de.u5b.pikdroid.system.render;

import android.content.Context;
import android.opengl.Matrix;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import de.u5b.pikdroid.component.Pose;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.entity.Entity;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.Topic;

/**
 * The OpenGLSurface on Android Devices
 * Created by Foxel on 17.08.2014.
 */
public class MySurfaceView extends GLSurfaceView {

    Engine engine;

    public MySurfaceView(Context context, Engine engine) {
        super(context);
        // set OpenGLES 2.0 usage
        setEGLContextClientVersion(2);
        // set the render
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

        engine.getEventManager().publish(new Event(Topic.SPAWN_PIKDROID, entity));
        return true;
    }

}
