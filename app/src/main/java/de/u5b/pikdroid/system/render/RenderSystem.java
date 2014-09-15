package de.u5b.pikdroid.system.render;

import android.opengl.Matrix;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import de.u5b.pikdroid.system.render.mesh.MeshFactory;
import de.u5b.pikdroid.system.render.object.ARenderObject;
import de.u5b.pikdroid.system.render.object.RenderObjectFactory;
import de.u5b.pikdroid.system.render.object.UniformColorRenderObject;
import de.u5b.pikdroid.system.render.shader.Shader;
import de.u5b.pikdroid.system.render.shader.ShaderLibrary;

/**
 * The RenderSystem draws Entities that have an Visual Component
 * Created by Foxel on 13.08.2014.
 */
public class RenderSystem extends ASystem implements GLSurfaceView.Renderer {

    private ShaderLibrary shaderLibrary;
    private HashMap<Visual.Shading, TreeMap<Integer, ARenderObject>> renderObjects;
    private float[] viewMatrix;
    private Engine engine; // TODO: this is only for update call --> remove this later

    public RenderSystem(Engine engine) {
        super(engine);
        this.engine = engine;

        // subscribe to topics
        eventManager.subscribe(EventTopic.ENTITY_CREATED,this);
        eventManager.subscribe(EventTopic.ENTITY_DELETED,this);

        renderObjects = new HashMap<Visual.Shading, TreeMap<Integer, ARenderObject>>();
        shaderLibrary = new ShaderLibrary();
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
        GLES20.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glEnable( GLES20.GL_DEPTH_TEST );
        GLES20.glDepthFunc( GLES20.GL_LEQUAL );
        GLES20.glDepthMask( true );
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ar = (float)height / (float)width;
        //Matrix.orthoM(viewMatrix,0,-10.0f,10.0f,-10.0f*ar,10.0f*ar,-1,1);

        Matrix.perspectiveM(viewMatrix, 0, 90, (float)width/(float)height,0.1f,100);
        float[] cam = new float[16];
        Matrix.setIdentityM(cam,0);
        Matrix.translateM(cam,0,0,0, -17f);
        Matrix.multiplyMM(viewMatrix,0,viewMatrix,0,cam,0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // TODO: remove this update
        engine.update();

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);





        Iterator it = renderObjects.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();

            // get the shader for the next objects to draw
            Shader shader = ShaderLibrary.getShader((Visual.Shading)pairs.getKey());
            shader.use();

            // get the List that contains the object
            TreeMap<Integer, ARenderObject> objList = (TreeMap<Integer, ARenderObject>)pairs.getValue();

            // Set Uniform Values
            int uView = GLES20.glGetUniformLocation(shader.getId(), "uView");
            GLES20.glUniformMatrix4fv(uView,1,false,viewMatrix,0);

            for (Map.Entry<Integer, ARenderObject> obj : objList.entrySet()) {
                obj.getValue().calcMP();

                GLES20.glEnableVertexAttribArray(0);
                obj.getValue().draw();
                GLES20.glDisableVertexAttribArray(0);
            }
        }



    }

    private void onEntityCreated(Event event) {

        // get the pose matrix from Entity
        float[] poseMatrix = ((Pose)event.getEntity().getComponent(Component.Type.POSE)).getMatrix();

        // get the visual component
        Visual visual = (Visual)event.getEntity().getComponent(Component.Type.VISUAL);
        float[] modelMatrix = visual.getModelMatrix();

        // Add a new RenderObject to the renderObject Collection
        if(!renderObjects.containsKey(visual.getShading())) {
            renderObjects.put(visual.getShading(), new TreeMap<Integer, ARenderObject>());
        }

        ARenderObject rObj = RenderObjectFactory.create(visual);
        rObj.setModelMatrix(modelMatrix);
        rObj.setPoseMatrix(poseMatrix);
        renderObjects.get(visual.getShading()).put(event.getEntity().getID(),rObj);
    }

    private void onEntityDeleted(Event event) {
        if (event.getEntity().hasComponent(Component.Type.VISUAL)) {
            Visual visual = (Visual) event.getEntity().getComponent(Component.Type.VISUAL);
            renderObjects.get(visual.getShading()).remove(event.getEntity().getID());
        }
    }

    private static int createShader(String vertex, String fragment) {
        int vs = compileShader(GLES20.GL_VERTEX_SHADER, vertex);
        int fs = compileShader(GLES20.GL_FRAGMENT_SHADER, fragment);
        int sp = GLES20.glCreateProgram();

        GLES20.glAttachShader(sp, vs);
        GLES20.glAttachShader(sp, fs);
        GLES20.glLinkProgram(sp);
        return sp;
    }

    private static int compileShader(int type, String code) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader,code);
        GLES20.glCompileShader(shader);
        return shader;
    }


}
