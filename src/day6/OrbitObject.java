package day6;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class OrbitObject {

    private String objectName;
    private List<OrbitObject> children;
    private OrbitObject parentObject;
    private int distanceFromYou;

    OrbitObject(String objectName) {
        this.objectName = objectName;
        children = new ArrayList<>();
    }

    void addChild(OrbitObject child) {
        children.add(child);
    }


    void addParent(OrbitObject parentObject) {
        this.parentObject = parentObject;
    }

    public OrbitObject getParentObject() {
        return parentObject;
    }

    public int getDistanceFromYou() {
        return distanceFromYou;
    }

    public void setDistanceFromYou(int distanceFromYou) {
        this.distanceFromYou = distanceFromYou;
    }

    public String getObjectName() {
        return objectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrbitObject)) return false;

        OrbitObject object = (OrbitObject) o;

        return Objects.equals(objectName, object.objectName);
    }

    @Override
    public int hashCode() {
        return objectName != null ? objectName.hashCode() : 0;
    }

    public List<OrbitObject> getChildren() {
        return children;
    }
}

class OrbitObjectComparator implements Comparator<OrbitObject> {

    @Override
    public int compare(OrbitObject o1, OrbitObject o2) {
        if (o1.getDistanceFromYou() < o2.getDistanceFromYou()) {
            return 1;
        } else if (o1.getDistanceFromYou() > o2.getDistanceFromYou()) {
            return -1;
        } else {
            return 0;
        }
    }
}