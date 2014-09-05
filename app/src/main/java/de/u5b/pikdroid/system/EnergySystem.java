package de.u5b.pikdroid.system;

import java.util.ArrayList;

import de.u5b.pikdroid.component.Component;
import de.u5b.pikdroid.component.Energy;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.entity.Entity;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.EventTopic;

/**
 * Created by Foxel on 30.08.2014.
 */
public class EnergySystem extends ASystem {
    ArrayList<Entity> entities;

    public EnergySystem(Engine engine) {
        super(engine);
        entities = new ArrayList<Entity>();

        // subscribe to topics
        eventManager.subscribe(EventTopic.ENTITY_CREATED,this);
        eventManager.subscribe(EventTopic.ENTITY_DELETED,this);
        eventManager.subscribe(EventTopic.TRY_ENERGY_TRANSFER,this);
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getEventTopic()) {
            case ENTITY_CREATED: onEntityCreated(event); break;
            case ENTITY_DELETED: onEntityDeleted(event); break;
            case TRY_ENERGY_TRANSFER: onTryEnergyTransfer(event); break;
        }
    }

    private void onEntityCreated(Event event) {
        if(event.getEntity().hasComponent(Component.Type.ENERGY)) {
            entities.add(event.getEntity());
        }
    }

    private void onEntityDeleted(Event event) {
        entities.remove(event.getEntity());
    }

    private void onTryEnergyTransfer(Event event) {
        Energy energyA = (Energy)event.getEntity().getComponent(Component.Type.ENERGY);
        Energy energyB = (Energy)event.getTarget().getComponent(Component.Type.ENERGY);
        if(energyA.containsEnergy()) {
            Energy.transfer(energyA,energyB);
            eventManager.publish(new Event(EventTopic.ON_ENERGY_TRANSFERRED, event.getEntity(),event.getTarget()));
            if(!energyA.containsEnergy()) {
                entityManager.delete(energyA.getEntity());
            }
        }
    }

    @Override
    public void update() {

    }
}
