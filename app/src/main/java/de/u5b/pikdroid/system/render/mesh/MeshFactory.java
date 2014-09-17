package de.u5b.pikdroid.system.render.mesh;

import java.util.HashMap;

import de.u5b.pikdroid.component.Visual;

/**
 * The MeshFactory creates Basic Meshes that are ready to use.
 * In Future maybe also Meshes from File.
 * Created by Foxel on 17.08.2014.
 */
public class MeshFactory {

    private static HashMap<Visual.Shading, Mesh> triangle = new HashMap<Visual.Shading, Mesh>();
    private static HashMap<Visual.Shading, Mesh> quad = new HashMap<Visual.Shading, Mesh>();

    /**
     * A simple triangle
     * @return Triangle Mesh
     */
    public static Mesh getTriangle(Visual.Shading shading) {
        if(!triangle.containsKey(shading))
            triangle.put(shading, createTriangle(shading));
        return triangle.get(shading);
    }

    /**
     * A simple triangle
     * @return Triangle Mesh
     */
    public static Mesh getQuad(Visual.Shading shading) {
        if(!quad.containsKey(shading))
            quad.put(shading, createQuad(shading));
        return quad.get(shading);
    }

    /**
     * Creates a basic Triangle
     * @return
     */
    private static Mesh createTriangle(Visual.Shading shading) {
        float vertices[] = {
                0.0f,  0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };
        return new Mesh(vertices, shading);
    }

    private static Mesh createQuad(Visual.Shading shading) {
        float vertices[] = {
                0.5f,  0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                -0.5f, 0.5f, 0.0f,

                0.5f, 0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                -0.5f,  -0.5f, 0.0f,
        };
        return new Mesh(vertices, shading);
    }
}
