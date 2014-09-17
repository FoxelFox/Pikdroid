package de.u5b.pikdroid.system.render.mesh;

import android.opengl.Matrix;

import java.io.InputStream;
import java.util.HashMap;

import de.u5b.pikdroid.component.Visual;
import de.u5b.pikdroid.game.Engine;

/**
 * The MeshFactory creates Basic Meshes that are ready to use.
 * In Future maybe also Meshes from File.
 * Created by Foxel on 17.08.2014.
 */
public class MeshFactory {

    private HashMap<Visual.Shading, Mesh> triangle;
    private HashMap<Visual.Shading, Mesh> quad;

    private HashMap<Visual.Shading, HashMap<String, Mesh>> meshMap;

    private Engine engine;

    public MeshFactory(Engine engine) {
        this.engine = engine;

        triangle = new HashMap<Visual.Shading, Mesh>();
        quad = new HashMap<Visual.Shading, Mesh>();
        meshMap = new HashMap<Visual.Shading, HashMap<String, Mesh>>();

    }

    /**
     * A simple triangle
     * @return Triangle Mesh
     */
    public Mesh getTriangle(Visual.Shading shading) {
        if(!triangle.containsKey(shading))
            triangle.put(shading, createTriangle(shading));
        return triangle.get(shading);
    }

    /**
     * A simple triangle
     * @return Triangle Mesh
     */
    public Mesh getQuad(Visual.Shading shading) {
        if(!quad.containsKey(shading))
            quad.put(shading, createQuad(shading));
        return quad.get(shading);
    }

    /**
     * A Mesh by the filename
     * @param name file name
     * @param shading shading shader
     * @return mesh from file
     */
    public Mesh get(String name, Visual.Shading shading) {
        if(!meshMap.containsKey(shading)) {
            meshMap.put(shading, new HashMap<String, Mesh>());
        }

        if(!meshMap.get(shading).containsKey(name)) {
            meshMap.get(shading).put(name, create(name,shading));
        }

        return meshMap.get(shading).get(name);
    }


    private Mesh create(String name, Visual.Shading shading) {
        InputStream file = engine.getInputStream(name + ".dae");
        ColladaModel colladaModel = new ColladaModel(file);
        return colladaModel.getMesh();
    }

    /**
     * Creates a basic Triangle
     * @return
     */
    private Mesh createTriangle(Visual.Shading shading) {
        float vertices[] = {
                0.0f,  0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };
        return new Mesh(vertices, shading);
    }

    private Mesh createQuad(Visual.Shading shading) {
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
