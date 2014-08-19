package de.u5b.pikdroid.component;

/**
 * Component for Entities that will have Intelligence
 * Created by Foxel on 19.08.2014.
 */
public class Intelligence extends Component{
    private int viewDistance; // how far can this intelligence look

    public Intelligence(int viewDistance) {
        this.viewDistance = viewDistance;
    }
}
