package de.u5b.pikdroid.system.detect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

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
    private HashMap<Pose, Integer> lastDetectorIndexX;  // contains the last detector Indices from listX
    private HashMap<Pose, Integer> lastDetectorIndexY;  // contains the last detector Indices from listY

    public Map2D (){
        listX = new ArrayList<Pose>();
        listY = new ArrayList<Pose>();
        lastDetectorIndexX = new HashMap<Pose, Integer>();
        lastDetectorIndexY = new HashMap<Pose, Integer>(); // Stores the last detector Indices
    }

    public void sort() {

        try {

            // sort x
            Collections.sort(listX, new Comparator<Pose>() {
                @Override
                public int compare(Pose lhs, Pose rhs) {

                    if (lhs.getPositionX() < rhs.getPositionX())
                        return -1;
                    else
                        return 1;

                }
            });

            // sort y
            Collections.sort(listY, new Comparator<Pose>() {
                @Override
                public int compare(Pose lhs, Pose rhs) {
                    if (lhs.getPositionY() < rhs.getPositionY())
                        return -1;
                    else
                        return 1;
                }
            });

        } catch (IllegalArgumentException e) {
            // float values x == y
        }

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
        //int ix = listX.indexOf(pose);
        //int iy = listY.indexOf(pose);
        int ix = myIndexOf(listX, lastDetectorIndexX.get(pose), pose);
        int iy = myIndexOf(listY, lastDetectorIndexY.get(pose), pose);
        lastDetectorIndexX.put(pose, ix);
        lastDetectorIndexY.put(pose, iy);

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

    public int myIndexOf(ArrayList<Pose> list, int startIndex, Pose item) {

        try {
            // TODO: here is going something wrong

            // first check if the index is the same
            if (list.get(startIndex).equals(item)) return startIndex;

            int index;
            int offset = 1;
            while (true) {
                // check right side
                index = startIndex + offset;
                if (index >= 0 && index < list.size() && list.get(index).equals(item)) {
                    return index;
                }

                // check left side
                index = startIndex - offset;
                if (index >= 0 && index < list.size() && list.get(index).equals(item)) {
                    return index;
                }
                ++offset;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void insert(Pose pose) {
        listX.add(pose);
        listY.add(pose);
        lastDetectorIndexX.put(pose, listX.size() / 2);
        lastDetectorIndexY.put(pose, listY.size() / 2);

    }

    public void remove(Pose pose) {
        listX.remove(pose);
        listY.remove(pose);
        lastDetectorIndexX.remove(pose);
        lastDetectorIndexY.remove(pose);
    }
}
