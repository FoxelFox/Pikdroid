package de.u5b.pikdroid.system.render.object;

import android.graphics.Color;

import de.u5b.pikdroid.system.render.mesh.Mesh;

/**
 * A Basic Object colored by an uniform in the shader
 * Created by Foxel on 19.08.2014.
 */
public class UniformColorRenderObject extends ARenderObject{
    Color color;

    public UniformColorRenderObject(Mesh mesh) {
        super(mesh);
        color = new Color();
    }

    public UniformColorRenderObject(Mesh mesh, Color color) {
        super(mesh);
        this.color = color;
    }

    @Override
    public void draw(int shaderProgram) {
        // TODO: apply uniform color
        mesh.draw(shaderProgram);
    }
}
