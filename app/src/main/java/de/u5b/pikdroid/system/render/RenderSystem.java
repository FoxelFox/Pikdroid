package de.u5b.pikdroid.system.render;

import android.graphics.Matrix;
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
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "uniform mat4 uPose;" +
                    "void main(){" +
                    "  gl_Position = uPose * vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "void main() {" +
                    "  gl_FragColor = vec4(0.1,0.1,0.1,1.0);" +
                    "}";

    private int shaderProgram;
    private LinkedList<ARenderObject> renderObjects;

    public RenderSystem(Engine engine) {
        super(engine);
        eventManager.subscribe(Topic.ENTITY_CREATED,this);
        renderObjects = new LinkedList<ARenderObject>();
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getTopic()) {
            case ENTITY_CREATED: onEntityCreated(event);
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f, 0.0f, 0.25f, 1.0f);
        shaderProgram = createShader(vertexShaderCode, fragmentShaderCode);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(shaderProgram);

        // draw all objects
        for (int i = 0; i < renderObjects.size(); ++i) {
            renderObjects.get(i).draw(shaderProgram);
        }
    }

    private void onEntityCreated(Event event) {

        // get the pose matrix from Entity
        float[] matrix = event.getEntity().getComponent(Pose.class).getMatrix();

        // get the visual component
        Visual vis = event.getEntity().getComponent(Visual.class);

        // add a new RenderObject to the renderObject List
        renderObjects.add(new UniformColorRenderObject(MeshFactory.getQuad(), vis.getColor(), matrix));

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
