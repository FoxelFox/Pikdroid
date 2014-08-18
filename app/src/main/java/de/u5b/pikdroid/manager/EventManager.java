package de.u5b.pikdroid.manager;

import java.util.ArrayList;
import java.util.HashMap;

import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.system.*;

/**
 * Created by Foxel on 14.08.2014.
 */
public class EventManager extends AManager {
    private HashMap<EventTopic, ArrayList<ASystem>> subscriber;

    public EventManager(Engine engine) {
        super(engine);
        subscriber = new HashMap<EventTopic, ArrayList<ASystem>>();
    }

    /**
     * Subscribe to an EventTopic
     * @param eventTopic Topic to subscribe on
     * @param system The ISystem that want to subscribe
     */
    public void subscribe(EventTopic eventTopic, ASystem system) {
        if(subscriber.containsKey(eventTopic)) {
            subscriber.get(eventTopic).add(system);
        } else {
            ArrayList<ASystem> list = new ArrayList<ASystem>();
            list.add(system);
            subscriber.put(eventTopic, list);
        }
    }

    /**
     * Publish a new  Event that all subscribers will receive.
     * @param eventTopic Event
     */
    public void publish(EventTopic eventTopic) {
        if(subscriber.containsKey(eventTopic)) {
            for(ASystem sys: subscriber.get(eventTopic)) {
                sys.handleEvent(eventTopic);
            }
        }

    }
}
