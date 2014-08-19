package de.u5b.pikdroid.component;

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
}
