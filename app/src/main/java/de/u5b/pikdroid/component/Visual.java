package de.u5b.pikdroid.component;

import android.opengl.Matrix;

/**
 * This Component is for the RenderSystem to draw it
 * Created by Foxel on 18.08.2014.
 */
public class Visual extends Component{
    private float[] color;          // The Color for an Entity
    private float[] modelMatrix;
    private Shading shading;

    private Geometry geometryType;
    private String geometryName;

    public Visual(float[] color, Shading shading, Geometry geometryType) {
        this.color = color;
        this.shading = shading;
        this.geometryType = geometryType;
        modelMatrix = new float[16];
        Matrix.setIdentityM(modelMatrix,0);
    }

    public float[] getColor() {
        return color;
    }

    public Shading getShading() {
        return shading;
    }

    public Geometry getGeometryType() {
        return geometryType;
    }

    public String getGeometryName() {
        return geometryName;
    }

    public void setGeometryName(String name) {
        this.geometryName = name;
    }

    public void scale(float x, float y, float z) {
        Matrix.scaleM(modelMatrix, 0, x, y, z);
    }

    public float[] getModelMatrix() {
        return modelMatrix;
    }

    public void setAlpha(float alpha) {
        color[3] = alpha;
    }

    @Override
    public Type getType() {
        return Type.VISUAL;
    }

    public enum Shading {
        TextureColor,
        UniformColor,
    }

    public enum Geometry {
        Triangle,
        Quad,
        Model,
    }
}
