package de.u5b.pikdroid.component;

import de.u5b.pikdroid.manager.entity.Entity;

/**
 * Component for Entities that will have Intelligence
 * Created by Foxel on 19.08.2014.
 */
public class Intelligence extends Component{
    private boolean hasFood;
    private Entity base;

    public Intelligence(Entity base) {
        this.base = base;
        this.hasFood = false;
    }

    public void setHasFood(boolean hasFood) {
        this.hasFood = hasFood;
    }

    public boolean hasFood() {
        return hasFood;
    }

    public Entity getBase() {
        return base;
    }

    public void setBase(Entity base) {
        this.base = base;
    }
}
