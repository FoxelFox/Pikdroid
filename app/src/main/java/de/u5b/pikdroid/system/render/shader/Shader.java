package de.u5b.pikdroid.system.render.shader;

import static android.opengl.GLES20.*;

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
        int vs = compile(GL_VERTEX_SHADER, vertex);
        int fs = compile(GL_FRAGMENT_SHADER, fragment);
        id = glCreateProgram();

        glAttachShader(id, vs);
        glAttachShader(id, fs);
        glLinkProgram(id);
    }

    private static int compile(int type, String code) {
        int shader = glCreateShader(type);
        glShaderSource(shader,code);
        glCompileShader(shader);
        return shader;
    }

    public int getId() {
        return id;
    }

    public void use() {
        glUseProgram(id);
        activeID = id;
    }

    public static int getActiveShaderID() {
        return activeID;
    }


}
