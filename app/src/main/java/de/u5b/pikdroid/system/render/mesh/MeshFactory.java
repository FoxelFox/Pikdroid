package de.u5b.pikdroid.system.render.mesh;

/**
 * Created by Foxel on 17.08.2014.
 */
public class MeshFactory {

    private static Mesh triangle;

    public static Mesh getTriangle() {
        if(triangle == null) triangle = createTriangle();
        return triangle;
    }

    private static Mesh createTriangle() {
        float vertices[] = {
                0.0f,  0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };
        return new Mesh(vertices);
    }
}
