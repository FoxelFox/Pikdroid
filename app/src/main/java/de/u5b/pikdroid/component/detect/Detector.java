package de.u5b.pikdroid.component.detect;

import java.util.ArrayList;

import de.u5b.pikdroid.component.Component;
import de.u5b.pikdroid.manager.entity.Entity;

/**
 * Created by Foxel on 25.08.2014.
 */
public class Detector extends Component{

    private ArrayList<ArrayList<DetectEntry>> detections;
    private ArrayList<DetectEntry> minDistanceEntries;



    public  Detector() {
        detections = new ArrayList<ArrayList<DetectEntry>>(DetectHint.values().length);
        minDistanceEntries = new ArrayList<DetectEntry>(DetectHint.values().length);

        for (int i = 0; i < DetectHint.values().length; ++i) {
            detections.add(new ArrayList<DetectEntry>());
            minDistanceEntries.add(null);
        }
    }

    public void setDetections(DetectHint hint, ArrayList<DetectEntry> detectedEntities, DetectEntry minDistanceEntry) {
        detections.set(hint.ordinal(), detectedEntities);
        minDistanceEntries.set(hint.ordinal(),  minDistanceEntry);
    }

    public ArrayList<DetectEntry> getDetections(DetectHint hint) {
        return detections.get(hint.ordinal());
    }

    public DetectEntry getMinDistanceDetection(DetectHint hint) {
        return minDistanceEntries.get(hint.ordinal());
    }

    @Override
    public Type getType() {
        return Type.DETECTOR;
    }
}
