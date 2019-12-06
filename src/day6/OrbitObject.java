package day6;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrbitObject {

    private String objectName;
    private List<OrbitObject> children;
    private OrbitObject parentObject;

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
}
