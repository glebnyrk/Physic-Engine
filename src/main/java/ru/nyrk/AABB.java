package ru.nyrk;

public class AABB {
    private Vector3 min, max;

    AABB(Vector3 min, Vector3 max) {
        this.min = min(min, max);
        this.max = max(min, max);
    }

    AABB(AABB... a) {
        min = new Vector3(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
        for (AABB b : a) {
            min = min(b.getMin(), min);
        }
        max = new Vector3(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
        for (AABB b : a) {
            max = max(b.getMin(), min);
        }
    }

    AABB(Hitbox hitbox) {
        float rr = hitbox.getRawRadius();
        Vector3 b = new Vector3(rr, rr, rr);
        Vector3 center = hitbox.getCenter();
        min = center.sub(b);
        max = center.add(b);
    }

    AABB(PhysicsBody physicsBody) {
        float rr = physicsBody.rawRadius();
        Vector3 b = new Vector3(rr, rr, rr);
        Vector3 center = physicsBody.getCenter();
        min = center.sub(b);
        max = center.add(b);
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

    private Vector3 min(Vector3 a, Vector3 b) {
        float xmin = Math.min(a.getX(), b.getX());
        float ymin = Math.min(a.getY(), b.getY());
        float zmin = Math.min(a.getZ(), b.getZ());
        return new Vector3(xmin, ymin, zmin);
    }

    private Vector3 max(Vector3 a, Vector3 b) {
        float xmax = Math.max(a.getX(), b.getX());
        float ymax = Math.max(a.getY(), b.getY());
        float zmax = Math.max(a.getZ(), b.getZ());
        return new Vector3(xmax, ymax, zmax);
    }

    public void normalize() {
        if (!min.lessThan(max)) {
            Vector3 nMax = max(max, min);
            Vector3 nMin = min(max, min);
            max = nMax;
            min = nMin;
        }
    }

    boolean collide(AABB other) {
        normalize();
        other.normalize();
        Vector3[] corners = corners();
        for (Vector3 corner : corners) {
            if (other.isInside(corner)) {
                return true;
            }
        }
        return false;
    }

    boolean isInside(Vector3 v) {
        normalize();
        return v.moreThan(min) && v.lessThan(max);
    }

    Vector3 center() {
        Vector3 size = size().mul(0.5f);
        return min.add(size);
    }

    Vector3 size() {
        normalize();
        return max.sub(min);
    }

    public float volume() {
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
