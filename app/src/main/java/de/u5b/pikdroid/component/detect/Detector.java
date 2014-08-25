package de.u5b.pikdroid.component.detect;

import java.util.ArrayList;

import de.u5b.pikdroid.component.Component;
import de.u5b.pikdroid.manager.entity.Entity;

/**
 * Created by Foxel on 25.08.2014.
 */
public class Detector extends Component{

    ArrayList<ArrayList<Entity>> detections;

    public  Detector() {
        detections = new ArrayList<ArrayList<Entity>>(DetectHint.values().length);
        for (int i = 0; i < DetectHint.values().length; ++i) {
            detections.add(new ArrayList<Entity>());
        }
    }

    public void setDetections(DetectHint hint, ArrayList<Entity> detectedEntities) {
        detections.set(hint.ordinal(), detectedEntities);
    }

    public ArrayList<Entity> getDetections(DetectHint hint) {
        return detections.get(hint.ordinal());
    }
}
