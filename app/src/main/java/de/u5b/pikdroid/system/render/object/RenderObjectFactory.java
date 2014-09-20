package de.u5b.pikdroid.system.render.object;

import de.u5b.pikdroid.component.Visual;
import de.u5b.pikdroid.game.Engine;
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

        // load and set the geometry to draw from file
        obj.setMesh(meshFactory.get(visual.getModelName(), visual.getShading()));
        return obj;
    }
}
