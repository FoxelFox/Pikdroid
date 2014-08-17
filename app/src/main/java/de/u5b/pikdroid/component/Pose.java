package de.u5b.pikdroid.component;

import android.graphics.Matrix;

/**
 * Created by Foxel on 18.08.2014.
 */
public class Pose {
    Matrix pose;

    public Pose() {
        pose = new Matrix();
    }

    public Pose(Matrix pose) {
        this.pose = pose;
    }

}
