package de.u5b.pikdroid.system.render.shader;

import java.util.HashMap;

import de.u5b.pikdroid.component.Visual;

/**
 * Library for all used shader
 * Created by Foxel on 11.09.2014.
 */
public class ShaderLibrary {
    private static HashMap<Visual.Shading, Shader> library = new HashMap<Visual.Shading, Shader>();

    public static Shader getShader(Visual.Shading shading) {
        if(library.containsKey(shading)) {
            return library.get(shading);
        } else {
            return createShader(shading);
        }
    }

    /**
     * Builds a new shader from identifier.
     * @return new shader
     */
    private static Shader createShader(Visual.Shading shading) {
        Shader shader = null;

        switch (shading) {
            case UniformColor: shader = new Shader(vUniColor, fUniColor); break;
            case TextureColor: shader = new Shader(vTexColor, fTexColor); break;
        }

        if(shader != null) {
            library.put(shading, shader);
        }
        return shader;
    }

    // TODO: Load this from a file
    // UniformColor Shader ////////////////////////////////////////////////////
    private static final String vUniColor =
            "attribute vec4 vPosition;" +
                    "uniform mat4 uMP;" +
                    "uniform mat4 uView;" +
                    "void main(){" +
                    "  gl_Position = uView * uMP * vPosition;" +
                    "}";

    private static final String fUniColor =
            "precision mediump float;" +
                    "uniform vec4 uColor;" +
                    "void main() {" +
                    "  gl_FragColor = uColor;" +
                    "}";
    ///////////////////////////////////////////////////////////////////////////

    // TextureColor Shader ////////////////////////////////////////////////////
    private static final String vTexColor =
            "attribute vec4 vPosition;"+
            "attribute vec2 vUV;"+

            "varying vec2 pUV;"+

            "uniform mat4 uMP;"+
            "uniform mat4 uView;"+

            "void main() {"+
            "  pUV = vUV;"+
            "  gl_Position = uView * uMP * vPosition;"+
            "}";

    private static final String fTexColor =
            "precision mediump float;"+
                    "varying vec2 pUV;"+
                    "uniform sampler2D tColor;"+
                    "void main() {"+
                    "  gl_FragColor = uColor;"+
                    "}";
    ///////////////////////////////////////////////////////////////////////////
}