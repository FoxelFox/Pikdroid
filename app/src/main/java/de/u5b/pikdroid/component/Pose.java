package de.u5b.pikdroid.component;

import android.graphics.Matrix;

/**
 * The Pose is the Entities position and rotation in the World
 * Created by Foxel on 18.08.2014.
 */
public class Pose extends Component{
    Matrix pose;

    public Pose() {
        pose = new Matrix();
    }

    public Pose(Matrix pose) {
        this.pose = pose;
    }

}
