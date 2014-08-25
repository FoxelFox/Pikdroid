package de.u5b.pikdroid.system;

import java.util.ArrayList;
import java.util.HashMap;

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

    }

    private void onEntityDeleted(Event event) {
        detectors.remove(event.getEntity());

        // if entity is detectable remove it from collection
        Detectable detectable = event.getEntity().getComponent(Detectable.class);
        if (detectable != null) {
            detectables.get(detectable.getHint().ordinal()).remove(detectable);
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
