package de.u5b.pikdroid.system.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import de.u5b.pikdroid.system.render.mesh.Mesh;
import de.u5b.pikdroid.system.render.mesh.MeshFactory;

/**
 * Created by Foxel on 13.08.2014.
 */
public class RenderSystem implements GLSurfaceView.Renderer {
    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;                        \n" +
                    "attribute vec4 vPosition;               \n" +
                    "void main(){                            \n" +
                    "  gl_Position = vPosition; \n" +
                    "}                                       \n";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "void main() {" +
                    "  gl_FragColor = vec4(0.1,0.1,0.1,1.0);" +
                    "}";

    private int shaderProgram;
    private Mesh triangle;


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f, 0.0f, 0.25f, 1.0f);
        shaderProgram = createShader(vertexShaderCode, fragmentShaderCode);
        triangle = MeshFactory.getTriangle();

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(shaderProgram);

        triangle.draw(shaderProgram);
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
