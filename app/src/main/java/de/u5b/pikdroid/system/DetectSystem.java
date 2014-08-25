package de.u5b.pikdroid.system;

import java.util.HashMap;

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

    HashMap<Integer, Entity> Hints;


    public DetectSystem(Engine engine) {
        super(engine);
        eventManager.subscribe(Topic.ENTITY_CREATED, this);
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getTopic()) {
            case ENTITY_CREATED: onCreateHint(event); break;
        }
    }

    private void onCreateHint(Event event) {

        // try to get relevant components
        Detectable detectable = event.getEntity().getComponent(Detectable.class);
        Detector detector = event.getEntity().getComponent(Detector.class);


        if(detectable != null) {
            // this Entity is detectable for Entities that have detectors
        }

        if(detector != null) {
            // this Entity is able to detect detectable Entities
        }
    }
}
