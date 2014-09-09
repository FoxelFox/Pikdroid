package de.u5b.pikdroid.system;

import android.opengl.Matrix;

import de.u5b.pikdroid.component.Pose;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.entity.Entity;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.EventTopic;

/**
 * Created by Foxel on 09.09.2014.
 */
public class InputSystem extends ASystem{

    public InputSystem(Engine engine) {
        super(engine);
    }

    @Override
    public void handleEvent(Event event) {

    }

    @Override
    public void update() {

    }

    public void onPressed(float x, float y) {
        float[] matrix = new float[16];
        Matrix.setIdentityM(matrix, 0);

        Matrix.translateM(matrix,0,x ,y, 0.0f);

        Entity entity = new Entity();
        entity.addComponent(new Pose(matrix));

        // async queued event
        eventManager.publishQueued(new Event(EventTopic.SET_USER_TARGET, entity));
    }

    public void onRelesed(float x, float y) {

    }

    public void onMoved(float dx, float dy) {

    }
}
