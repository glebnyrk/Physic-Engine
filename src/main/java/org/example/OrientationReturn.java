package org.example;

public abstract class OrientationReturn {
    abstract Vector3 getCenter();
    abstract Vector3 getSize();
    abstract Quaternion getRotation();
    public final Vector3 translateToLocal(Vector3 point){
        Vector3 lpd = getCenter().sub(point);
        return new Vector3(getRotation().back().mul(new Quaternion(lpd)).mul(getRotation()));
    }
    public final Vector3 translateToGlobal(Vector3 point){
        Vector3 gcp = new Vector3(getRotation().mul(new Quaternion(point)).mul(getRotation().back()));
        return getCenter().add(gcp);
    }
}
