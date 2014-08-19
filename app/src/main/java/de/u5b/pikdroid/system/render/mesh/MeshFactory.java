package de.u5b.pikdroid.system.render.mesh;

/**
 * The MeshFactory creates Basic Meshes that are ready to use.
 * In Future maybe also Meshes from File.
 * Created by Foxel on 17.08.2014.
 */
public class MeshFactory {

    private static Mesh triangle;
    private static Mesh quad;

    /**
     * A simple triangle
     * @return Triangle Mesh
     */
    public static Mesh getTriangle() {
        if(triangle == null) triangle = createTriangle();
        return triangle;
    }

    /**
     * A simple triangle
     * @return Triangle Mesh
     */
    public static Mesh getQuad() {
        if(quad == null) quad = createQuad();
        return quad;
    }

    /**
     * Creates a basic Triangle
     * @return
     */
    private static Mesh createTriangle() {
        float vertices[] = {
                0.0f,  0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };
        return new Mesh(vertices);
    }

    private static Mesh createQuad() {
        float vertices[] = {
                0.5f,  0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                -0.5f, 0.5f, 0.0f,

                0.5f, 0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                -0.5f,  -0.5f, 0.0f,
        };
        return new Mesh(vertices);
    }
}
