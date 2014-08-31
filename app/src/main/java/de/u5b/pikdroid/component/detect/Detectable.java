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
        strength = 0;
    }

    public DetectHint getHint() {
        return hint;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        if(strength < 0)
            this.strength = 0;
        else
            this.strength = strength;
    }

    @Override
    public Type getType() {
        return Type.DETECTABLE;
    }
}
