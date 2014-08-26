package de.u5b.pikdroid.system;

import java.util.ArrayList;
import java.util.HashMap;

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

    ArrayList<ArrayList<Entity>> detectables; // HashArray[DetectHint] of Entities
    ArrayList<Entity> detectors;

    public DetectSystem(Engine engine) {
        super(engine);

        // subscribe to topics
        eventManager.subscribe(Topic.ENTITY_CREATED, this);
        eventManager.subscribe(Topic.ENTITY_DELETED, this);

        // init HashArray[DetectHint] with ArrayLists<Entity>
        detectables = new ArrayList<ArrayList<Entity>>(DetectHint.values().length);
        for (int i = 0; i < DetectHint.values().length; ++i) {
            detectables.add(new ArrayList<Entity>());
        }

        detectors = new ArrayList<Entity>();
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getTopic()) {
            case ENTITY_CREATED: onEntityCreated(event); break;
            case ENTITY_DELETED: onEntityDeleted(event); break;
        }
    }

    @Override
    public void update() {

        // For all Detector Entities
        for (int i = 0; i < detectors.size(); ++i) {
            Entity iDetector = detectors.get(i);

            // for every List by DetectHint
            for (int k = 0; k < DetectHint.values().length; ++k) {
                ArrayList<Entity> kDetectableList = detectables.get(k);
                ArrayList<DetectEntry> kDetected = new ArrayList<DetectEntry>();
                DetectEntry kMinDistanceEntry = null;

                // for every Detectable in List
                for (int m = 0; m < kDetectableList.size(); ++m) {
                    Entity mDetectable = kDetectableList.get(m);

                    // check if Detector can detect Detectable
                    float mDistance = detectedDistance(iDetector, mDetectable);
                    if(mDistance < 100.0f) {
                        kMinDistanceEntry = new DetectEntry(mDetectable, mDistance);
                        kDetected.add(new DetectEntry(mDetectable, mDistance));
                    }
                }
                // add detected entities for Hint to Detector
                iDetector.getComponent(Detector.class).setDetections(DetectHint.values()[k], kDetected, kMinDistanceEntry);
            }
        }
    }

    private float detectedDistance(Entity detector, Entity detectable) {
        Pose p1 = detector.getComponent(Pose.class);
        Pose p2 = detectable.getComponent(Pose.class);

        return p1.distance(p2);
    }

    private void onEntityDeleted(Event event) {
        detectors.remove(event.getEntity());

        // if entity is detectable remove it from collection
        Detectable detectable = event.getEntity().getComponent(Detectable.class);
        if (detectable != null) {
            detectables.get(detectable.getHint().ordinal()).remove(event.getEntity());
        }
    }

    private void onEntityCreated(Event event) {

        // try to get relevant components
        Detectable detectable = event.getEntity().getComponent(Detectable.class);
        Detector detector = event.getEntity().getComponent(Detector.class);


        if(detectable != null) {
            // this Entity is detectable for Entities that have detectors
            detectables.get(detectable.getHint().ordinal()).add(event.getEntity());
        }

        if(detector != null) {
            // this Entity is able to detect detectable Entities
            detectors.add(event.getEntity());
        }
    }
}
