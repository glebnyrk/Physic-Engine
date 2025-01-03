package ru.nyrk.hitboxes;

import ru.nyrk.BVH.AABLike;
import ru.nyrk.maths.Vector3;

public class AABB implements AABLike {
    private Vector3 min = Vector3.ZERO;
    private Vector3 max = Vector3.ZERO;

    public AABB(Vector3 min, Vector3 max) {
        this.min = min.min(max);
        this.max = max.max(min);
    }

    public AABB(Iterable<? extends AABLike> a) {
        reorganize(a);
    }

    public AABB(AABLike a, AABLike b) {
        reorganize(a, b);
    }

    public void reorganize(Iterable<? extends AABLike> list) {
        min = Vector3.INFINITY;
        max = Vector3.NEGATIVE_INFINITY;
        for (AABLike b : list) {
            if (b == null) {
                continue;
            }
            min = b.getMin().min(min);
            max = max.max(b.getMax());
        }
    }

    public void reorganize(AABLike a, AABLike b) {
        if (a == null && b == null) {
            Vector3 center = getCenter();
            min = center;
            max = center;
            return;
        }
        if (a != null && b != null) {
            min = b.getMin().min(a.getMin());
            max = b.getMax().max(a.getMax());
        } else if (a == null) {
            min = b.getMin();
            max = b.getMax();
        } else if (b == null) {
            min = a.getMin();
            max = a.getMax();
        }
    }

    @Override
    public Vector3 getCenter() {
        return min.add(size());
    }

    @Override
    public Vector3 getSize() {
        return max.sub(min).mul(0.5f);
    }

    public Vector3 getMin() {
        return min;
    }

    public void setMin(Vector3 v) {
        min = v;
    }

    public Vector3 getMax() {
        return max;
    }

    public void setMax(Vector3 v) {
        max = v;
    }

    public void normalize() {
        Vector3 nMax = max.max(min);
        Vector3 nMin = min.min(max);
        max = nMax;
        min = nMin;
    }

    public boolean collide(AABB other) {
        Vector3 max1 = other.max;
        Vector3 min1 = other.min;
        return (max1.getX() > min.getX() && min1.getX() < max.getX() &&
                max1.getY() > min.getY() && min1.getY() < max.getY() &&
                max1.getZ() > min.getZ() && min1.getZ() < max.getZ());
    }

    public boolean isInside(Vector3 v) {
        normalize();
        return v.moreThan(min) && v.lessThan(max);
    }

    public Vector3 center() {
        Vector3 size = size().mul(0.5f);
        return min.add(size);
    }

    public Vector3 size() {
        normalize();
        return max.sub(min);
    }

    public float getVolume() {
        Vector3 s = size();
        return s.getX() * s.getY() * s.getZ();
    }

    public Vector3[] corners() {
        normalize();
        Vector3 dSize = max.sub(min);
        return new Vector3[]{
                min,
                min.add(new Vector3(0, 0, dSize.getZ())),
                min.add(new Vector3(0, dSize.getY(), 0)),
                min.add(new Vector3(0, dSize.getY(), dSize.getZ())),
                min.add(new Vector3(dSize.getX(), 0, 0)),
                min.add(new Vector3(dSize.getX(), 0, dSize.getZ())),
                min.add(new Vector3(dSize.getX(), dSize.getY(), 0)),
                max
        };
    }

    @Override
    public String toString() {
        return "AABB{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AABB aabb) {
            return min.equals(aabb.min) && max.equals(aabb.max);
        } else return false;
    }
}
