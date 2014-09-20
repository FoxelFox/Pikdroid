package de.u5b.pikdroid.system.render.mesh;

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

    private HashMap<Visual.Shading, HashMap<String, Mesh>> meshMap;

    private Engine engine;

    public MeshFactory(Engine engine) {
        this.engine = engine;
        meshMap = new HashMap<Visual.Shading, HashMap<String, Mesh>>();
    }

    /**
     * Returns A mesh that will/was loaded from a file
     * @param name File name without file extension.
     * @param shading Visual.Shading Group
     * @return a mesh from file to draw it
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
        ColladaModel model = new ColladaModel(file);

        return new Mesh(model.getInterleavedVertexBuffer(), model.getPolyCount(), model.getSemantics(), model.getStrides(), shading );
    }
}
