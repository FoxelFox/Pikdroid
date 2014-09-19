package de.u5b.pikdroid.system.render.mesh;

import static android.opengl.GLES20.*;

import java.nio.FloatBuffer;

import de.u5b.pikdroid.component.Visual;
import de.u5b.pikdroid.system.render.shader.ShaderLibrary;

/**
 * A Mesh is a 3D Polygon collection on the GPU.
 * It is used to draw Objects on OpenGL.
 * Created by Foxel on 17.08.2014.
 */
public class Mesh {

    private FloatBuffer vertices;
    private Semantic[] semantics;
    private int[] strides;
    private int atributeOffset;
    private int drawragne;
    private int attributes[];

    /**
     * Create a new Mesh with the @vertices
     */
    public Mesh(FloatBuffer vertices, int polyCount, Semantic[] semantics, int[] strides,  Visual.Shading shading) {
        this.vertices = vertices;
        this.strides = strides;
        this.drawragne = polyCount * 3;
        this.semantics = semantics;
        this.atributeOffset = 0;


        attributes = new int[semantics.length];
        for (int i = 0; i < attributes.length; ++i) {
            switch (semantics[i]) {
                case VERTEX:
                    attributes[i] = glGetAttribLocation(ShaderLibrary.getShader(shading).getId(), "vPosition");
                    break;
                case NORMAL:
                    attributes[i] = glGetAttribLocation(ShaderLibrary.getShader(shading).getId(), "vNormal");
                    break;
                case TEXCOORD:
                    attributes[i] = glGetAttribLocation(ShaderLibrary.getShader(shading).getId(), "vUV");
                    break;
            }
            atributeOffset += strides[i];
        }
        atributeOffset *= 4; // float -> 4 byte
    }

    /**
     * Draw the Mesh in OpenGL to a Screen or Framebuffer
     */
    public void draw() {
        for (int i = 0; i < semantics.length; ++i) {
            glVertexAttribPointer(attributes[i], strides[i], GL_FLOAT, false, atributeOffset, vertices);
        }
        glDrawArrays(GL_TRIANGLES, 0, drawragne);
    }


    public enum Semantic {
        VERTEX,
        NORMAL,
        TEXCOORD,
    }

}
