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
     * @param dx local x-axis dx
     * @param dy local y-axis dx
     * @param dz local z-axis dx
     */
    public void translate(float dx, float dy, float dz) {
        Matrix.translateM(matrix, 0, dx, dy, dz);
    }

    /**
     * Rotate the pose around itself
     * @param angle angle to rotate
     * @param x x-axis
     * @param y y-axis
     * @param z z-axis
     */
    public void rotate(float angle, float x, float y, float z) {
        float tmpX = matrix[12];
        float tmpY = matrix[13];
        float tmpZ = matrix[14];

        matrix[12] = matrix[13] = matrix[14] = 0.0f;

        Matrix.rotateM(matrix,0,angle,x,y,z);

        matrix[12] = tmpX;
        matrix[13] = tmpY;
        matrix[14] = tmpZ;
    }

    /**
     * Calculate the euclidean distance
     * @param pose other Pose
     * @return euclidean distance
     */
    public float distance(Pose pose) {
        return (float)Math.sqrt(Math.pow(matrix[12] - pose.matrix[12],2) +
                                Math.pow(matrix[13] - pose.matrix[13],2) +
                                Math.pow(matrix[14] - pose.matrix[14],2));
    }

    /**
     * Calculate a float[3] ray to @target
     * @param target ray target
     * @return ray from this to target
     */
    public float[] ray(Pose target) {
        float[] dirVec = new float[3];
        dirVec[0] = target.matrix[12] - matrix[12];
        dirVec[1] = target.matrix[13] - matrix[13];
        dirVec[2] = target.matrix[14] - matrix[14];
        return dirVec;
    }

    /**
     * Calculate a normalized float[3] ray to @target
     * @param target ray target
     * @return normalized ray from this to target
     */
    public  float[] nray(Pose target) {
        float dist = distance(target);
        float[] r = ray(target);
        r[0] /= dist;
        r[1] /= dist;
        r[2] /= dist;
        return r;
    }
}
