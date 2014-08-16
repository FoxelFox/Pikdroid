package de.u5b.pikdroid.system;

import de.u5b.pikdroid.manager.EventTopic;

/**
 * Created by Foxel on 13.08.2014.
 */
public interface ISystem {

    public void handleEvent(EventTopic eventTopic);
}
