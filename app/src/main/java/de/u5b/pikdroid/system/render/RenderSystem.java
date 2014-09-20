package de.u5b.pikdroid.system.render;

import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import de.u5b.pikdroid.component.Component;
import de.u5b.pikdroid.component.Pose;
import de.u5b.pikdroid.component.Visual;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.EventTopic;
import de.u5b.pikdroid.system.ASystem;
import de.u5b.pikdroid.system.render.object.ARenderObject;
import de.u5b.pikdroid.system.render.object.RenderObjectFactory;
import de.u5b.pikdroid.system.render.shader.Shader;
import de.u5b.pikdroid.system.render.shader.ShaderLibrary;

import static android.opengl.GLES20.*;

/**
 * The RenderSystem draws Entities that have an Visual Component
 * Created by Foxel on 13.08.2014.
 */
public class RenderSystem extends ASystem implements GLSurfaceView.Renderer {

    private HashMap<Visual.Shading, TreeMap<Integer, ARenderObject>> renderObjects;
    private float[] viewMatrix;
    private Engine engine; // TODO: this is only for update call --> remove this later
    private RenderObjectFactory rObjFactory;

    public RenderSystem(Engine engine) {
        super(engine);
        this.engine = engine;

        // subscribe to topics
        eventManager.subscribe(EventTopic.ENTITY_CREATED,this);
        eventManager.subscribe(EventTopic.ENTITY_DELETED,this);

        renderObjects = new HashMap<Visual.Shading, TreeMap<Integer, ARenderObject>>();
        rObjFactory = new RenderObjectFactory(engine);
        viewMatrix = new float[16];
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getEventTopic()) {
            case ENTITY_CREATED: onEntityCreated(event); break;
            case ENTITY_DELETED: onEntityDeleted(event); break;
        }
    }

    @Override
    public void update() {
        // TODO: here it would be nice to draw
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        glDepthMask(true);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);

        Matrix.perspectiveM(viewMatrix, 0, 40, (float)width/(float)height,0.1f,1000);
        float[] cam = new float[16];
        Matrix.setIdentityM(cam,0);
        Matrix.translateM(cam,0,0,0, -50f);
        Matrix.multiplyMM(viewMatrix,0,viewMatrix,0,cam,0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // TODO: remove this update
        engine.update();

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        for (Object o : renderObjects.entrySet()) {
            Map.Entry visualTree = (Map.Entry) o;

            // get the shader for the next objects to draw
            Shader shader = ShaderLibrary.getShader((Visual.Shading) visualTree.getKey());
            shader.use();

            // get the List that contains the object
            TreeMap<Integer, ARenderObject> objList = (TreeMap<Integer, ARenderObject>) visualTree.getValue();

            // Set Uniform Values
            int uView = glGetUniformLocation(shader.getId(), "uView");
            glUniformMatrix4fv(uView, 1, false, viewMatrix, 0);

            for (Map.Entry<Integer, ARenderObject> obj : objList.entrySet()) {
                obj.getValue().calcMP();

                glEnableVertexAttribArray(0);
                glEnableVertexAttribArray(1);
                glEnableVertexAttribArray(2);
                obj.getValue().draw();
                glDisableVertexAttribArray(0);
                glEnableVertexAttribArray(1);
                glEnableVertexAttribArray(2);
            }
        }
    }

    private void onEntityCreated(Event event) {

        if(event.getEntity().hasComponent(Component.Type.VISUAL)) {

            // get the pose matrix from Entity
            float[] poseMatrix = ((Pose) event.getEntity().getComponent(Component.Type.POSE)).getMatrix();

            // get the visual component
            Visual visual = (Visual) event.getEntity().getComponent(Component.Type.VISUAL);
            float[] modelMatrix = visual.getModelMatrix();

            // Add a new RenderObject to the renderObject Collection
            if (!renderObjects.containsKey(visual.getShading())) {
                renderObjects.put(visual.getShading(), new TreeMap<Integer, ARenderObject>());
            }

            ARenderObject rObj = rObjFactory.create(visual);
            rObj.setModelMatrix(modelMatrix);
            rObj.setPoseMatrix(poseMatrix);

            // insert object to the render collection
            renderObjects.get(visual.getShading()).put(event.getEntity().getID(), rObj);
        }
    }

    private void onEntityDeleted(Event event) {
        if (event.getEntity().hasComponent(Component.Type.VISUAL)) {
            Visual visual = (Visual) event.getEntity().getComponent(Component.Type.VISUAL);
            renderObjects.get(visual.getShading()).remove(event.getEntity().getID());
        }
    }
}
