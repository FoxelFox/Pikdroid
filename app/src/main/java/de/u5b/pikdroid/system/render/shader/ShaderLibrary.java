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
        library.put(shading, shader);

        return shader;
    }

    // TODO: Load this from a file
    // UniformColor Shader ////////////////////////////////////////////////////
    private static final String vUniColor =
            "attribute vec4 vPosition;" +

                    "attribute vec4 vNormal;" +
                    "varying vec4 pColor;" +
                    "uniform mat4 uMP;" +
                    "uniform mat4 uPose;" +
                    "uniform mat4 uView;" +
                    "uniform vec4 uColor;" +
                    "void main(){" +
                    "  mat4 n = uPose;" +
                    "  n[3] = vec4(0,0,0,1.0);" +
                    "  pColor = dot((n * vNormal).xyz, normalize(vec3(1.0,0.5,1.0))) * uColor + 0.25 * uColor;" +
                    "  pColor.a = 1.0;" +
                    "  gl_Position = uView * uMP * vPosition;" +
                    "}";

    private static final String fUniColor =
            "precision mediump float;" +
                    "varying vec4 pColor;" +
                    "void main() {" +
                    "  gl_FragColor = pColor;" +
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