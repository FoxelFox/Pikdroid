package de.u5b.pikdroid.system.render.object;

import java.io.InputStream;

import de.u5b.pikdroid.component.Visual;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.system.input.InputSystem;
import de.u5b.pikdroid.system.render.mesh.ColladaModel;
import de.u5b.pikdroid.system.render.mesh.MeshFactory;

/**
 * Creates a RenderObject based on the VisualComponent for an Entity.
 * Created by Foxel on 11.09.2014.
 */
public class RenderObjectFactory {

    private MeshFactory meshFactory;

    public RenderObjectFactory(Engine engine) {
        meshFactory = new MeshFactory(engine);
    }

    public ARenderObject create(Visual visual) {
        ARenderObject obj = null;

        // Type of RenderObject
        switch (visual.getShading()) {
            case UniformColor:
                obj = new UniformColorRenderObject(visual);
                break;
            case TextureColor:
                obj = new TextureColorRenderObject(visual);
                break;
        }

        // Add geometry
        switch (visual.getGeometryType()) {
            case Quad:
                obj.setMesh(meshFactory.getQuad(visual.getShading()));
                break;
            case Triangle:
                obj.setMesh(meshFactory.getTriangle(visual.getShading()));
                break;
            case Model:


                break;
        }

        return obj;
    }
}
