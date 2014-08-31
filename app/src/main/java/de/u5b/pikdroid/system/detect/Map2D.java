package de.u5b.pikdroid.system.detect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.u5b.pikdroid.component.Component;
import de.u5b.pikdroid.component.Pose;
import de.u5b.pikdroid.component.detect.DetectHint;
import de.u5b.pikdroid.component.detect.Detectable;
import de.u5b.pikdroid.component.detect.Detector;
import de.u5b.pikdroid.manager.entity.Entity;

/**
 * Created by Foxel on 31.08.2014.
 */
public class Map2D {
    private ArrayList<Pose> listX;
    private ArrayList<Pose> listY;

    public Map2D (){
        listX = new ArrayList<Pose>();
        listY = new ArrayList<Pose>();
    }

    public void sort() {

        // sort x
        Collections.sort(listX, new Comparator<Pose>() {
            @Override
            public int compare(Pose lhs, Pose rhs) {
                if(lhs.getPositionX() < rhs.getPositionX())
                    return -1;
                else
                    return 1;
            }
        });

        // sort y
        Collections.sort(listY, new Comparator<Pose>() {
            @Override
            public int compare(Pose lhs, Pose rhs) {
                if(lhs.getPositionY() < rhs.getPositionY())
                    return -1;
                else
                    return 1;
            }
        });
    }

    public void detectDetectables(Entity detector) {
        Pose pose = (Pose)detector.getComponent(Component.Type.POSE);
        Entity[] detections = ((Detector)detector.getComponent(Component.Type.DETECTOR)).getDetections();

        // first clear old detections
        int i = -1;
        int size = DetectHint.values().length;
        while (++i < size) {
            detections[i] = null;
        }

        // find list index
        int ix = listX.indexOf(pose);
        int iy = listY.indexOf(pose);


        i = ix;
        while (++i >= 0 && i < listX.size() && listX.get(i).distance(pose) < 2.0f) {
            parse(detections, listX.get(i).getEntity());
        }

        i = ix;
        while (--i >= 0 && i < listX.size() && listX.get(i).distance(pose) < 2.0f) {
            parse(detections, listX.get(i).getEntity());
        }

        i = iy;
        while (++i >= 0 && i < listY.size() && listY.get(i).distance(pose) < 2.0f) {
            parse(detections, listY.get(i).getEntity());
        }

        i = iy;
        while (--i >= 0 && i < listY.size() && listY.get(i).distance(pose) < 2.0f) {
            parse(detections, listY.get(i).getEntity());
        }

    }

    public void parse(Entity[] detections, Entity entity) {

        Detectable detectable = (Detectable)entity.getComponent(Component.Type.DETECTABLE);

        if(detectable != null) {
            int i = detectable.getHint().ordinal();
            if (detections[i] == null) {
                detections[i] = entity;
            }
        }
    }

    public void insert(Pose pose) {
        listX.add(pose);
        listY.add(pose);
    }

    public void remove(Pose pose) {
        listX.remove(pose);
        listY.remove(pose);
    }
}
