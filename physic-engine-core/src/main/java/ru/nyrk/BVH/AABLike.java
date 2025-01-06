package ru.nyrk.BVH;

import ru.nyrk.maths.Vector3;

public interface AABLike {
    Vector3 getCenter();


    Vector3 getSize();

    default Vector3 getMin() {
        float rawRadius = getRawRadius();
        return getCenter().sub(new Vector3(rawRadius, rawRadius, rawRadius));
    }

    default Vector3 getMax() {
        float rawRadius = getRawRadius();
        return getCenter().add(new Vector3(rawRadius, rawRadius, rawRadius));
    }

    default float getVolume() {
        Vector3 hs = getSize();
        return hs.getX() * hs.getY() * hs.getZ();
    }

    default float getRawRadius() {
        return getSize().getX();
    }
}
