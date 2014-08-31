package de.u5b.pikdroid.system.render.object;


import android.opengl.GLES20;
import android.opengl.Matrix;

import de.u5b.pikdroid.system.render.mesh.Mesh;

/**
 * A Basic Object colored by an uniform in the shader
 * Created by Foxel on 19.08.2014.
 */
public class UniformColorRenderObject extends ARenderObject{
    private float[] color;
    private float[] poseMatrix;
    private float[] modelMatrix;

    private float[] uMP;

    public UniformColorRenderObject(Mesh mesh, float[] color, float[] poseMatrix, float[] modelMatrix) {
        super(mesh);
        this.color = color;
        this.poseMatrix = poseMatrix;
        this.modelMatrix = modelMatrix;
        uMP = new float[16];
    }


    @Override
    public void draw(int vPosition, int uMPi, int uColor) {

        // set uniform shader color
        GLES20.glUniform4fv(uColor,1,color,0);

        // set shader uniform model/pose matrix
        Matrix.multiplyMM(uMP,0,poseMatrix,0,modelMatrix,0);

        GLES20.glUniformMatrix4fv(uMPi,1,false,uMP,0);

        mesh.draw(vPosition);
    }
}
