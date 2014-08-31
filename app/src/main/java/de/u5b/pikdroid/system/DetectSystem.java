package de.u5b.pikdroid.system;

import java.util.ArrayList;
import java.util.HashMap;

import de.u5b.pikdroid.component.Component;
import de.u5b.pikdroid.component.Pose;
import de.u5b.pikdroid.component.detect.DetectEntry;
import de.u5b.pikdroid.component.detect.DetectHint;
import de.u5b.pikdroid.component.detect.Detectable;
import de.u5b.pikdroid.component.detect.Detector;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.entity.Entity;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.Topic;

/**
 * Created by Foxel on 25.08.2014.
 */
public class DetectSystem extends ASystem {

    ArrayList<ArrayList<Pose>> detectables; // HashArray[DetectHint] of Entities
    ArrayList<Pose> detectors;

    public DetectSystem(Engine engine) {
        super(engine);

        // subscribe to topics
        eventManager.subscribe(Topic.ENTITY_CREATED, this);
        eventManager.subscribe(Topic.ENTITY_DELETED, this);

        // init HashArray[DetectHint] with ArrayLists<Entity>
        detectables = new ArrayList<ArrayList<Pose>>(DetectHint.values().length);
        for (int i = 0; i < DetectHint.values().length; ++i) {
            detectables.add(new ArrayList<Pose>());
        }

        detectors = new ArrayList<Pose>();
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getTopic()) {
            case ENTITY_CREATED: onEntityCreated(event); break;
            case ENTITY_DELETED: onEntityDeleted(event); break;
        }
    }

    /**
     * Detect Detectable with Detector
     */
    @Override
    public void update() {

        // TODO: Optimize this! Maybe use a QuadTree.
        // For all Detector Entities
        for (int i = 0; i < detectors.size(); ++i) {
            Pose iDetectorPose = detectors.get(i);

            // for every List by DetectHint
            for (int k = 0; k < DetectHint.values().length; ++k) {
                ArrayList<Pose> kDetectableList = detectables.get(k);
                ArrayList<DetectEntry> kDetected = new ArrayList<DetectEntry>();
                DetectEntry kMinDistanceEntry = null;

                // for every Detectable in List
                for (int m = 0; m < kDetectableList.size(); ++m) {
                    Pose mDetectablePose = kDetectableList.get(m);

                    // check if Detector can detect Detectable
                    float mDistance = iDetectorPose.distance(mDetectablePose);
                    if(mDistance < 2.0f) {

                        // is this the minimum distance?
                        if(kMinDistanceEntry == null) {
                            kMinDistanceEntry = new DetectEntry(mDetectablePose.getEntity(), mDistance);
                        } else if (kMinDistanceEntry.getDistance() > mDistance) {
                            kMinDistanceEntry = new DetectEntry(mDetectablePose.getEntity(), mDistance);
                        }

                        // add to detected list
                        kDetected.add(new DetectEntry(mDetectablePose.getEntity(), mDistance));
                    }
                }
                // add detected entities for Hint to Detector
                ((Detector)iDetectorPose.getEntity().getComponent(Component.Type.DETECTOR)).setDetections(DetectHint.values()[k], kDetected, kMinDistanceEntry);
            }
        }
    }

    private float detectedDistance(Pose p1, Pose p2) {
        return p1.distance(p2);
    }

    private void onEntityDeleted(Event event) {
        detectors.remove((Pose)event.getEntity().getComponent(Component.Type.POSE));

        // if entity is detectable remove it from collection
        Detectable detectable = (Detectable)event.getEntity().getComponent(Component.Type.DETECTABLE);
        if (detectable != null) {
            detectables.get(detectable.getHint().ordinal()).remove((Pose)event.getEntity().getComponent(Component.Type.POSE));
        }
    }

    private void onEntityCreated(Event event) {

        // try to get relevant components
        Detectable detectable = (Detectable)event.getEntity().getComponent(Component.Type.DETECTABLE);
        Detector detector = (Detector)event.getEntity().getComponent(Component.Type.DETECTOR);


        if(detectable != null) {
            // this Entity is detectable for Entities that have detectors
            detectables.get(detectable.getHint().ordinal()).add((Pose)event.getEntity().getComponent(Component.Type.POSE));
        }

        if(detector != null) {
            // this Entity is able to detect detectable Entities
            detectors.add((Pose)event.getEntity().getComponent(Component.Type.POSE));
        }
    }
}
