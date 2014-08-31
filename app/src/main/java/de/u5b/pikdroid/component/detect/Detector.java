package de.u5b.pikdroid.component.detect;

import de.u5b.pikdroid.component.Component;
import de.u5b.pikdroid.manager.entity.Entity;

/**
 * Created by Foxel on 25.08.2014.
 */
public class Detector extends Component{

    private Entity[] detections;

    public  Detector() {
        detections = new Entity[DetectHint.values().length];
    }

    public Entity[] getDetections() {
        return detections;
    }

    public Entity getDetection(DetectHint hint) {
        return detections[hint.ordinal()];
    }


    @Override
    public Type getType() {
        return Type.DETECTOR;
    }
}
