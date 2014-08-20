package de.u5b.pikdroid.system.render;

import android.opengl.Matrix;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.util.LinkedList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import de.u5b.pikdroid.component.Pose;
import de.u5b.pikdroid.component.Visual;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.Topic;
import de.u5b.pikdroid.system.ASystem;
import de.u5b.pikdroid.system.render.mesh.MeshFactory;
import de.u5b.pikdroid.system.render.object.ARenderObject;
import de.u5b.pikdroid.system.render.object.UniformColorRenderObject;

/**
 * The RenderSystem draws Entities that have an Visual Component
 * Created by Foxel on 13.08.2014.
 */
public class RenderSystem extends ASystem implements GLSurfaceView.Renderer {
    private final String vertexShaderCode =

            "attribute vec4 vPosition;" +
            "uniform mat4 uMP;" +
            "uniform mat4 uView;" +
            "void main(){" +
            "  gl_Position = uView * uMP * vPosition;" +
            "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
            "uniform vec4 uColor;" +
            "void main() {" +
            "  gl_FragColor = uColor;" +
            "}";

    private int shaderProgram;
    private LinkedList<ARenderObject> renderObjects;
    private float[] viewMatrix;

    public RenderSystem(Engine engine) {
        super(engine);
        eventManager.subscribe(Topic.ENTITY_CREATED,this);
        renderObjects = new LinkedList<ARenderObject>();
        viewMatrix = new float[16];
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getTopic()) {
            case ENTITY_CREATED: onEntityCreated(event);
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        shaderProgram = createShader(vertexShaderCode, fragmentShaderCode);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float s = 1.0f/10.f;
        if(width > height) {
            float ar = (float)height / (float)width;
            //Matrix.orthoM(viewMatrix,0,-s,s,-s*ar,s*ar,1.0f,10.0f);
            Matrix.setIdentityM(viewMatrix,0);
            Matrix.scaleM(viewMatrix,0,s*ar,s,1.0f);
        } else {
            float ar = (float)width / (float)height;
            //Matrix.orthoM(viewMatrix,0,-s*ar,s*ar,-s,s,1.0f,10.0f);
            Matrix.setIdentityM(viewMatrix,0);
            Matrix.scaleM(viewMatrix,0,s,s*ar,1.0f);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // TODO: remove this update
        eventManager.publish(new Event(Topic.UPDATE_INTELLIGENCE,null));

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(shaderProgram);


        int uView = GLES20.glGetUniformLocation(shaderProgram, "uView");
        GLES20.glUniformMatrix4fv(uView,1,false,viewMatrix,0);

        // draw all objects
        for (int i = 0; i < renderObjects.size(); ++i) {
            renderObjects.get(i).draw(shaderProgram);
        }
    }

    private void onEntityCreated(Event event) {

        // get the pose matrix from Entity
        float[] poseMatrix = event.getEntity().getComponent(Pose.class).getMatrix();

        // get the visual component
        float[] color = event.getEntity().getComponent(Visual.class).getColor();
        float[] modelMatrix = event.getEntity().getComponent(Visual.class).getModelMatrix();

        // add a new RenderObject to the renderObject List
        renderObjects.add(new UniformColorRenderObject(MeshFactory.getQuad(), color, poseMatrix, modelMatrix));

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
