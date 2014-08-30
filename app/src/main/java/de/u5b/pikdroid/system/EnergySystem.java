package de.u5b.pikdroid.system;

import java.util.ArrayList;

import de.u5b.pikdroid.component.Energy;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.entity.Entity;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.Topic;

/**
 * Created by Foxel on 30.08.2014.
 */
public class EnergySystem extends ASystem {
    ArrayList<Entity> entities;

    public EnergySystem(Engine engine) {
        super(engine);
        entities = new ArrayList<Entity>();
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getTopic()) {
            case ENTITY_CREATED: onEntityCreated(event); break;
            case ENTITY_DELETED: onEntityDeleted(event); break;
            case TRY_ENERGY_TRANSFER: onTryEnergyTransfer(event); break;
        }
    }

    private void onEntityCreated(Event event) {
        if(event.getEntity().getComponent(Energy.class) != null) {
            entities.add(event.getEntity());
        }
    }

    private void onEntityDeleted(Event event) {
        entities.remove(event.getEntity());
    }

    private void onTryEnergyTransfer(Event event) {
        Energy energyA = event.getEntity().getComponent(Energy.class);
        Energy energyB = event.getEntity().getComponent(Energy.class);
        if(energyA.containsEnergy()) {
            Energy.transfer(energyA,energyB);
            eventManager.publish(new Event(Topic.ON_ENERGY_TRANSFERRED, event.getEntity(),event.getTarget()));
        }
    }

    @Override
    public void update() {

    }
}
