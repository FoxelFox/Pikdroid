package de.u5b.pikdroid.system.render.object;


import android.opengl.GLES20;
import android.opengl.Matrix;

import de.u5b.pikdroid.component.Visual;
import de.u5b.pikdroid.system.render.mesh.Mesh;
import de.u5b.pikdroid.system.render.shader.Shader;

/**
 * A Basic Object colored by an uniform in the shader
 * Created by Foxel on 19.08.2014.
 */
public class UniformColorRenderObject extends ARenderObject{

    private int colorIndex;
    private float[] color;

    public UniformColorRenderObject(Visual visual) {
        super(visual);

        this.color = visual.getColor();
        colorIndex = GLES20.glGetUniformLocation(shader.getId(), "uColor");
    }


    @Override
    public void draw() {

        // set uniform shader color
        GLES20.glUniform4fv(colorIndex,1,color,0);
        GLES20.glUniform4fv(modelPoseMatrixIndex,1,modelPoseMatrix,0);
        GLES20.glUniformMatrix4fv(modelPoseMatrixIndex,1,false,modelPoseMatrix,0);

        mesh.draw();
    }
}
