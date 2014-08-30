package de.u5b.pikdroid.component.detect;

import de.u5b.pikdroid.component.Component;

/**
 * Created by Foxel on 25.08.2014.
 */
public class Detectable extends Component{

    private DetectHint hint;
    private int strength;

    public Detectable(DetectHint hint) {
        this.hint = hint;
        strength = 1;
    }

    public DetectHint getHint() {
        return hint;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
