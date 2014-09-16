package de.u5b.pikdroid.system.render.object;

import de.u5b.pikdroid.component.Visual;
import de.u5b.pikdroid.system.render.mesh.MeshFactory;

/**
 * Creates a RenderObject based on the VisualComponent for an Entity.
 * Created by Foxel on 11.09.2014.
 */
public class RenderObjectFactory {

    public static ARenderObject create(Visual visual) {
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
                obj.setMesh(MeshFactory.getQuad(visual.getShading()));
                break;
            case Triangle:
                obj.setMesh(MeshFactory.getTriangle(visual.getShading()));
                break;
            case Model:
                // TODO: Load geometry from a file!
                break;
        }

        return obj;
    }
}
