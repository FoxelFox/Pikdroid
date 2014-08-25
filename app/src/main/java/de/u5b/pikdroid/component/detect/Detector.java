package de.u5b.pikdroid.component.detect;

import de.u5b.pikdroid.component.Component;
import de.u5b.pikdroid.manager.entity.Entity;

/**
 * Created by Foxel on 25.08.2014.
 */
public class Detector extends Component{

    private Entity[][] detections;

    public  Detector() {
        detections = new Entity[DetectHint.values().length][];
    }

    public void setDetections(DetectHint hint, Entity[] detectedEntitys) {
        detections[hint.ordinal()] = detectedEntitys;
    }

    public Entity[] getDetections(DetectHint hint) {
        return detections[hint.ordinal()];
    }
}
