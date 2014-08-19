package de.u5b.pikdroid.component;

import android.graphics.Color;

/**
 * This Component is for the RenderSystem to draw it
 * Created by Foxel on 18.08.2014.
 */
public class Visual extends Component{
    Color color;    // The Color for an Entity

    public Visual() {
        color = new Color();
    }

    public Color getColor() {
        return color;
    }
}
