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
     * @param eTopic Topic to subscribe on
     * @param system The ISystem that want to subscribe
     */
    public void subscribe(EventTopic eTopic, ISystem system) {
        if(subscriber.containsKey(eTopic)) {
            subscriber.get(eTopic).add(system);
        } else {
            ArrayList<ISystem> list = new ArrayList<ISystem>();
            list.add(system);
            subscriber.put(eTopic, list);
        }
    }
}
