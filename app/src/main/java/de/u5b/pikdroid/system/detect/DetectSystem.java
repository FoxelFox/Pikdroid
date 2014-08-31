package de.u5b.pikdroid.system.detect;

import java.util.ArrayList;

import de.u5b.pikdroid.component.Component;
import de.u5b.pikdroid.component.Pose;
import de.u5b.pikdroid.component.detect.Detectable;
import de.u5b.pikdroid.component.detect.Detector;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.entity.Entity;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.Topic;
import de.u5b.pikdroid.system.ASystem;

/**
 * Detect n Detectable with m Detector
 * Created by Foxel on 25.08.2014.
 */
public class DetectSystem extends ASystem {

    private Map2D detectingMap;
    private ArrayList<Entity> detectors;

    public DetectSystem(Engine engine) {
        super(engine);

        // subscribe to topics
        eventManager.subscribe(Topic.ENTITY_CREATED, this);
        eventManager.subscribe(Topic.ENTITY_DELETED, this);
        eventManager.subscribe(Topic.NEW_POSE_SECTOR_REACHED, this);

        detectingMap = new Map2D();
        detectors = new ArrayList<Entity>();
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getTopic()) {
            case ENTITY_CREATED: onEntityCreated(event); break;
            case ENTITY_DELETED: onEntityDeleted(event); break;
            case NEW_POSE_SECTOR_REACHED: onNewPoseSectorReached(); break;
        }
    }

    private void onNewPoseSectorReached() {
        detectingMap.sort();
    }

    /**
     * Detect Detectable with Detector
     */
    @Override
    public void update() {
        for (Entity detector : detectors) {
            detectingMap.detectDetectables(detector);
        }
    }

    private void onEntityDeleted(Event event) {
        detectors.remove(event.getEntity());
        if(event.getEntity().hasComponent(Component.Type.POSE)) {
            Pose pose = (Pose)event.getEntity().getComponent(Component.Type.POSE);
            detectingMap.remove(pose);
        }
    }

    private void onEntityCreated(Event event) {

        // try to get relevant components
        Detectable detectable = (Detectable)event.getEntity().getComponent(Component.Type.DETECTABLE);
        Detector detector = (Detector)event.getEntity().getComponent(Component.Type.DETECTOR);


        if(detectable != null) {
            // this Entity is detectable for Entities that have detectors
            detectingMap.insert((Pose)event.getEntity().getComponent(Component.Type.POSE));
        }

        if(detector != null) {
            // this Entity is able to detect detectable Entities
            detectors.add(event.getEntity());
            detectingMap.insert((Pose)event.getEntity().getComponent(Component.Type.POSE));
        }
        detectingMap.sort();
    }
}
