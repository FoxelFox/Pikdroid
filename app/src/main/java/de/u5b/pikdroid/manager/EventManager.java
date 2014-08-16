package de.u5b.pikdroid.manager;

import java.util.ArrayList;
import java.util.HashMap;

import de.u5b.pikdroid.system.*;

/**
 * Created by Foxel on 14.08.2014.
 */
public class EventManager {
    private HashMap<EventTopic, ArrayList<ISystem>> subscriber;

    public EventManager() {
        subscriber = new HashMap<EventTopic, ArrayList<ISystem>>();
    }

    /**
     * Subscribe to an EventTopic
     * @param eventTopic Topic to subscribe on
     * @param system The ISystem that want to subscribe
     */
    public void subscribe(EventTopic eventTopic, ISystem system) {
        if(subscriber.containsKey(eventTopic)) {
            subscriber.get(eventTopic).add(system);
        } else {
            ArrayList<ISystem> list = new ArrayList<ISystem>();
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
            for(ISystem sys: subscriber.get(eventTopic)) {
                sys.handleEvent(eventTopic);
            }
        }

    }
}
