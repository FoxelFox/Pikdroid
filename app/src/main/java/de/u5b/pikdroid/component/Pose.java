package de.u5b.pikdroid.component;

import android.opengl.Matrix;

/**
 * The Pose is the Entities position and rotation in the World
 * Created by Foxel on 18.08.2014.
 */
public class Pose extends Component{
    private float[] matrix;

    public Pose() {
        matrix = new float[16];
    }

    public Pose(float[] matrix) {
        this.matrix = matrix;
    }

    public float[] getMatrix() {
        return matrix;
    }

    public void translate(float x, float y, float z) {
        Matrix.translateM(matrix, 0, x, y, z);
    }

    public void rotate(float angle, float x, float y, float z) {
        //Matrix.
        //am.setValues(matrix);
        //am.postRotate(angle,x,y);
        //am.getValues(matrix);
    }
}
