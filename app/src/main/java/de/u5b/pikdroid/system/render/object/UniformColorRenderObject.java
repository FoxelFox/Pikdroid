package de.u5b.pikdroid.system.render.object;

import android.graphics.Color;
import android.graphics.Matrix;
import android.opengl.GLES20;

import de.u5b.pikdroid.system.render.mesh.Mesh;

/**
 * A Basic Object colored by an uniform in the shader
 * Created by Foxel on 19.08.2014.
 */
public class UniformColorRenderObject extends ARenderObject{
    private Color color;
    private float[] matrix;

    public UniformColorRenderObject(Mesh mesh, Color color, float[] matrix) {
        super(mesh);
        this.color = color;
        this.matrix = matrix;
    }


    @Override
    public void draw(int shaderProgram) {
        // TODO: apply uniform color

        int uPose = GLES20.glGetUniformLocation(shaderProgram,"uPose");
        GLES20.glUniformMatrix4fv(uPose,1,false,matrix,0);

        mesh.draw(shaderProgram);
    }
}
