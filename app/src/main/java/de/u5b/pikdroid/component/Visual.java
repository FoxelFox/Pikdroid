package de.u5b.pikdroid.component;

import android.opengl.Matrix;

/**
 * This Component is for the RenderSystem to draw it
 * Created by Foxel on 18.08.2014.
 */
public class Visual extends Component{
    float[] color;          // The Color for an Entity
    float[] modelMatrix;

    public Visual(float[] color) {
        this.color = color;
        modelMatrix = new float[16];
        Matrix.setIdentityM(modelMatrix,0);
    }

    public float[] getColor() {
        return color;
    }

    public void scale(float x, float y, float z) {
        Matrix.scaleM(modelMatrix, 0, x, y, z);
    }

    public float[] getModelMatrix() {
        return modelMatrix;
    }
}
