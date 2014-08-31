package de.u5b.pikdroid.system.render.object;

import de.u5b.pikdroid.system.render.mesh.Mesh;

/**
 * Contains a Shader and a mesh to Draw
 * Created by Foxel on 19.08.2014.
 */
public abstract class ARenderObject {
    protected String vertexShaderCode;
    protected String fragmentShaderCode;

    protected Mesh mesh;

    public ARenderObject(Mesh mesh) {
        this.mesh = mesh;
    }

    public abstract void draw(int vPosition, int uMPi, int uColor);
}
