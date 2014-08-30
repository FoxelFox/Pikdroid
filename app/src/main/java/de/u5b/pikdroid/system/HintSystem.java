package de.u5b.pikdroid.system;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import de.u5b.pikdroid.component.Pose;
import de.u5b.pikdroid.component.Visual;
import de.u5b.pikdroid.component.detect.DetectHint;
import de.u5b.pikdroid.component.detect.Detectable;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.manager.entity.Entity;
import de.u5b.pikdroid.manager.event.Event;
import de.u5b.pikdroid.manager.event.Topic;

/**
 * Created by Foxel on 30.08.2014.
 */
public class HintSystem extends ASystem {

    private HashMap<Pair<Integer,Integer>, Entity> hints;
    private int size;

    public HintSystem(Engine engine) {
        super(engine);
        size = 16;
        hints = new HashMap<Pair<Integer,Integer>, Entity>();

        eventManager.subscribe(Topic.MAKE_HINT, this);
        eventManager.subscribe(Topic.REMOVE_HINT, this);
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getTopic()) {
            case MAKE_HINT: onMakeHint(event, true); break;
            case REMOVE_HINT: onMakeHint(event, false); break;
        }
    }

    private void onMakeHint(Event event, boolean make) {
        Pose pose = event.getEntity().getComponent(Pose.class);
        int x = (int)(pose.getPositionX());
        int y = (int)(pose.getPositionY());

        Pose hintPose = new Pose();
        hintPose.translate(x,y,0);

        Pair key = Pair.create(x,y);
        if(!hints.containsKey(key)) {
            hints.put(key, createHint(hintPose));
        }

        Detectable d = hints.get(key).getComponent(Detectable.class);
        d.setStrength(d.getStrength() + 1);

        if(make)
            d.setStrength(d.getStrength() + 2);
        else
            d.setStrength(d.getStrength() - 2);

        hints.get(key).getComponent(Visual.class).setAlpha((float)d.getStrength() * 0.05f);
    }

    @Override
    public void update() {

    }

    private Entity createHint(Pose pose) {
        Entity hint = new Entity();

        Visual vis = new Visual(new float[] { 0.0f, 0.5f, 1.0f, 0.1f });

        Detectable detectable = new Detectable(DetectHint.FOOD_MARK);

        hint.addComponent(detectable);
        hint.addComponent(pose);
        hint.addComponent(vis);

        entityManager.add(hint);

        return  hint;
    }
}
