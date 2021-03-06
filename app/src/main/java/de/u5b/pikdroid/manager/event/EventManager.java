package de.u5b.pikdroid.manager.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.AManager;
import de.u5b.pikdroid.system.*;

/**
 * The EventManager is used to receive and send Events between Systems
 * Events will be send over EventTopics that can Systems subscribe / publish
 * Created by Foxel on 14.08.2014.
 */
public class EventManager extends AManager {
    private HashMap<EventTopic, ArrayList<ASystem>> subscriber;  // contains the Systems that subscribe to Topics
    private ConcurrentLinkedQueue<Event> eventQueue;

    public EventManager(Engine engine) {
        super(engine);
        subscriber = new HashMap<EventTopic, ArrayList<ASystem>>();
        eventQueue = new ConcurrentLinkedQueue<Event>();
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
     * @param event Event to publish
     */
    public void publish(Event event) {
        if(subscriber.containsKey(event.getEventTopic())) {
            for(ASystem sys: subscriber.get(event.getEventTopic())) {
                sys.handleEvent(event);
            }
        }
        if(event.getEntity() != null)
            event.getEntity().notify(event.getEventTopic());

        if(event.getTarget() != null)
            event.getTarget().notify(event.getEventTopic());
    }

    public void update() {
        while (!eventQueue.isEmpty())
            publish(eventQueue.poll());
    }

    public void publishQueued(Event event) {
        eventQueue.offer(event);
    }
}
