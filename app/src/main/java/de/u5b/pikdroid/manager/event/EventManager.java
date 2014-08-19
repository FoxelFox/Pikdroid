package de.u5b.pikdroid.manager.event;

import java.util.ArrayList;
import java.util.HashMap;

import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.AManager;
import de.u5b.pikdroid.manager.entity.Entity;
import de.u5b.pikdroid.system.*;

/**
 * The EventManager is used to receive and send Events between Systems
 * Events will be send over EventTopics that can Systems subscribe / publish
 * Created by Foxel on 14.08.2014.
 */
public class EventManager extends AManager {
    private HashMap<Topic, ArrayList<ASystem>> subscriber;  // contains the Systems that subscribe to Topics

    public EventManager(Engine engine) {
        super(engine);
        subscriber = new HashMap<Topic, ArrayList<ASystem>>();
    }

    /**
     * Subscribe to an EventTopic
     * @param topic Topic to subscribe on
     * @param system The ISystem that want to subscribe
     */
    public void subscribe(Topic topic, ASystem system) {
        if(subscriber.containsKey(topic)) {
            subscriber.get(topic).add(system);
        } else {
            ArrayList<ASystem> list = new ArrayList<ASystem>();
            list.add(system);
            subscriber.put(topic, list);
        }
    }

    /**
     * Publish a new  Event that all subscribers will receive.
     * @param event Event to publish
     */
    public void publish(Event event) {
        if(subscriber.containsKey(event.getTopic())) {
            for(ASystem sys: subscriber.get(event.getTopic())) {
                sys.handleEvent(event);
            }
        }
    }
}
