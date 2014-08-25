package de.u5b.pikdroid.component.detect;

import de.u5b.pikdroid.component.Component;

/**
 * Created by Foxel on 25.08.2014.
 */
public class Detectable extends Component{

    private DetectHint hint;

    public Detectable(DetectHint hint) {
        this.hint = hint;
    }

    public DetectHint getHint() {
        return hint;
    }

}
