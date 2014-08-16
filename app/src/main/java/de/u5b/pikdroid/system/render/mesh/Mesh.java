package de.u5b.pikdroid.system.render.mesh;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Foxel on 17.08.2014.
 */
public class Mesh {

    private FloatBuffer vbo;

    public Mesh(float[] vertices) {
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());

        vbo = bb.asFloatBuffer();
        vbo.put(vertices);
        vbo.position(0);
    }

    public void draw(int shaderProgram) {
        // TODO Optimize
        int vPosition = GLES20.glGetAttribLocation(shaderProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(vPosition);
        GLES20.glVertexAttribPointer(vPosition, 3, GLES20.GL_FLOAT, false, 0, vbo);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
        GLES20.glDisableVertexAttribArray(vPosition);
    }

}
