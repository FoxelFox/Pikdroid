package de.u5b.pikdroid.system.render.object;

import static android.opengl.GLES20.*;

import de.u5b.pikdroid.component.Visual;

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
        colorIndex = glGetUniformLocation(shader.getId(), "uColor");
    }


    @Override
    public void draw() {

        // set uniform shader color
        glUniform4fv(colorIndex,1,color,0);
        glUniformMatrix4fv(poseMatrixIndex,1,false,poseMatrix,0);
        glUniformMatrix4fv(modelPoseMatrixIndex,1,false,modelPoseMatrix,0);

        mesh.draw();
    }
}
