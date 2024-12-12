package org.example;

public abstract class Hitbox extends OrientationReturn{
    public abstract boolean collidesWith(Hitbox other);
    public final boolean rawCollideCheck(Hitbox other){
        float radius_sum = other.getRawRadius() + getRawRadius();
        float dictation_sum = other.getCenter().distance(this.getCenter());
        return dictation_sum <= radius_sum;
    }
    public abstract boolean contains(Vector3 point);
    public abstract float getRawRadius();
    public abstract Vector3 getCenter();
    public abstract Vector3 getSize();
    public abstract Quaternion getRotation();
    public abstract boolean isStatic();
}
