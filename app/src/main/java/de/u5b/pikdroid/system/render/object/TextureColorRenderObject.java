package de.u5b.pikdroid.system.render.object;

import static android.opengl.GLES20.*;

import de.u5b.pikdroid.component.Visual;

/**
 * A colored Object by an Texture
 * Created by Foxel on 10.09.2014.
 */
public class TextureColorRenderObject extends ARenderObject{

    public TextureColorRenderObject(Visual visual) {
        super(visual);
    }

    @Override
    public void draw() {
        glUniformMatrix4fv(shader.getId(),1,false,modelPoseMatrix,0);
        mesh.draw();
    }
}
