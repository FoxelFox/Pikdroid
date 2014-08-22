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
        Matrix.setIdentityM(matrix,0);
    }

    public Pose(float[] matrix) {
        this.matrix = matrix;
    }

    public float[] getMatrix() {
        return matrix;
    }

    /**
     * translate in local directions
     * @param x local x-axis dx
     * @param y local y-axis dx
     * @param z local z-axis dx
     */
    public void translate(float dx, float dy, float dz) {
        Matrix.translateM(matrix, 0, dx, dy, dz);
    }

    public void rotate(float angle, float x, float y, float z) {
        //Matrix.
        //am.setValues(matrix);
        //am.postRotate(angle,x,y);
        //am.getValues(matrix);
    }

    /**
     * Calculate the euclidean distance
     * @param pose other Pose
     * @return euclidean distance
     */
    public float distance(Pose pose) {
        return (float)Math.sqrt(Math.pow(matrix[ 3] - pose.matrix[ 3],2) +
                                Math.pow(matrix[ 7] - pose.matrix[ 7],2) +
                                Math.pow(matrix[11] - pose.matrix[11],2));
    }
}
