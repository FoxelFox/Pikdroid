package de.u5b.pikdroid.system.render.shader;

import android.opengl.GLES20;

/**
 * A simple structure for shader sources
 * Created by Foxel on 11.09.2014.
 */
public class Shader {

    private static int activeID = 0;

    private String vertex;          // Vertex Source Code
    private String fragment;        // Fragment Source Code
    private int id;                 // Shader Program ID from OpenGL

    public Shader(String vertex, String fragment) {
        this.vertex = vertex;
        this.fragment = fragment;
        build();
    }

    private void build() {
        int vs = compile(GLES20.GL_VERTEX_SHADER, vertex);
        int fs = compile(GLES20.GL_FRAGMENT_SHADER, fragment);
        id = GLES20.glCreateProgram();

        GLES20.glAttachShader(id, vs);
        GLES20.glAttachShader(id, fs);
        GLES20.glLinkProgram(id);
    }

    private static int compile(int type, String code) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader,code);
        GLES20.glCompileShader(shader);
        return shader;
    }

    public int getId() {
        return id;
    }

    public void use() {
        GLES20.glUseProgram(id);
        activeID = id;
    }

    public static int getActiveShaderID() {
        return activeID;
    }


}
