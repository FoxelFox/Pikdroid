package de.u5b.pikdroid.component;

/**
 * This Component is for the RenderSystem to draw it
 * Created by Foxel on 18.08.2014.
 */
public class Visual extends Component{
    float[] color;    // The Color for an Entity

    public Visual(float[] color) {
        this.color = color;
    }

    public float[] getColor() {
        return color;
    }
}
