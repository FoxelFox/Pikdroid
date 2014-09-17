package de.u5b.pikdroid.system.render.mesh;

import static android.opengl.GLES20.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import de.u5b.pikdroid.component.Visual;
import de.u5b.pikdroid.system.render.shader.ShaderLibrary;

/**
 * A Mesh is a 3D Polygon collection on the GPU.
 * It is used to draw Objects on OpenGL.
 * Created by Foxel on 17.08.2014.
 */
public class Mesh {

    private FloatBuffer vfb;            // VertexFloatBuffer
    private int vertexCount;            // Number of Vertices
    private int attributes[];

    /**
     * Create a new Mesh with the @vertices
     * @param vertices 3D Vertices
     */
    public Mesh(float[] vertices, Visual.Shading shading) {
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());

        vfb = bb.asFloatBuffer();
        vfb.put(vertices);
        vfb.position(0);

        vertexCount = vertices.length / 3;
        attributes = new int[1];

        attributes[0] = glGetAttribLocation(ShaderLibrary.getShader(shading).getId(), "vPosition");

    }

    /**
     * Draw the Mesh in OpenGL to a Screen or Framebuffer
     */
    public void draw() {
        glVertexAttribPointer(attributes[0], 3, GL_FLOAT, false, 0, vfb);
        glDrawArrays(GL_TRIANGLES, 0, vertexCount);
    }

}
