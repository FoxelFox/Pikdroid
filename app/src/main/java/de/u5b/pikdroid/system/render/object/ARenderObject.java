package de.u5b.pikdroid.system.render.object;

import static android.opengl.GLES20.*;
import android.opengl.Matrix;

import de.u5b.pikdroid.component.Visual;
import de.u5b.pikdroid.system.render.mesh.Mesh;
import de.u5b.pikdroid.system.render.shader.Shader;
import de.u5b.pikdroid.system.render.shader.ShaderLibrary;

/**
 * Contains a Shader and a mesh to Draw
 * Created by Foxel on 19.08.2014.
 */
public abstract class ARenderObject {

    protected Shader shader;

    // shader indices
    protected int poseMatrixIndex;      // shader index for Pose Matrix
    protected int modelPoseMatrixIndex; // shader index for Model*Pose Matrix

    protected Mesh mesh;                // geometry to draw
    protected float[] modelPoseMatrix;  // Uniform Model*Pose Matrix
    protected float[] poseMatrix;       // Matrix from Pose Component
    protected float[] modelMatrix;      // Matrix from Visual Component

    public ARenderObject(Visual visual) {
        shader = ShaderLibrary.getShader(visual.getShading());
        modelPoseMatrix = new float[16];
        modelPoseMatrixIndex = glGetUniformLocation(shader.getId(), "uMP");
        poseMatrixIndex = glGetUniformLocation(shader.getId(), "uPose");
    }

    public abstract void draw();

    /**
     * Calculates the Model*Pose Matrix
     * Note: if the Object is static you don't need to call this function
     */
    public void calcMP() {
        Matrix.multiplyMM(modelPoseMatrix, 0, poseMatrix, 0, modelMatrix, 0);
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public void setPoseMatrix(float[] poseMatrix){
        this.poseMatrix = poseMatrix;
    }

    public void setModelMatrix(float[] modelMatrix) {
        this.modelMatrix = modelMatrix;
    }

}
