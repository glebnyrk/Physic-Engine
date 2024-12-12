package org.example;

public class BoxHitbox extends Hitbox {
    private final boolean isStatic;
    private final OrientationReturn orientation;
    private Vector3 position;
    private Vector3 size;
    private Quaternion rotation;
    private float rawRadius;

    BoxHitbox(Vector3 position, Vector3 size, Quaternion rotation) {
        this.position = position;
        this.size = size;
        this.rotation = rotation;
        this.isStatic = true;
        orientation = null;
    }

    BoxHitbox(Vector3 position, Vector3 size) {
        this.position = position;
        this.size = size;
        this.rotation = new Quaternion(1, 0, 0, 0);
        this.isStatic = true;
        orientation = null;
    }

    BoxHitbox(Vector3 position) {
        this.position = position;
        this.size = new Vector3(1, 1, 1);
        this.rotation = new Quaternion(1, 0, 0, 0);
        this.isStatic = true;
        orientation = null;
    }

    BoxHitbox(OrientationReturn orientation) {
        this.isStatic = false;
        this.orientation = orientation;
    }

    @Override
    public boolean collidesWith(Hitbox other) {
        if (!rawCollideCheck(other)) {
            return false;
        }
        if (other instanceof BoxHitbox box) {
            if (mediumBoxCollideCheck(box)) {
                return true;
            } else {
                return clearBoxCollideCheck(box);
            }
        } else {
            return false;
        }

    }

    public boolean clearBoxCollideCheck(BoxHitbox other) {
        Vector3 xAxis = new Vector3(1, 0, 0);
        Vector3 yAxis = new Vector3(0, 1, 0);
        Vector3 zAxis = new Vector3(0, 0, 1);
        Vector3 x = new Vector3(getRotation()).mul(xAxis);
        Vector3 y = new Vector3(getRotation()).mul(yAxis);
        Vector3 z = new Vector3(getRotation()).mul(zAxis);
        Vector3 x1 = new Vector3(other.getRotation()).mul(xAxis);
        Vector3 y1 = new Vector3(other.getRotation()).mul(yAxis);
        Vector3 z1 = new Vector3(other.getRotation()).mul(zAxis);
        Vector3[] axes = new Vector3[]{
                x.normalize(),
                y.normalize(),
                z.normalize(),
                x1.normalize(),
                y1.normalize(),
                z1.normalize(),
                x.mul(x1).normalize(),
                x.mul(y1).normalize(),
                x.mul(z1).normalize(),
                y.mul(x1).normalize(),
                y.mul(y1).normalize(),
                y.mul(z1).normalize(),
                z.mul(x1).normalize(),
                z.mul(y1).normalize(),
                z.mul(z1).normalize()
        };
        for (Vector3 axis : axes) {
            float[] p1 = projection(axis);
            float[] p2 = other.projection(axis);
            float p1max = max(p1);
            float p2max = max(p2);
            float p1min = min(p1);
            float p2min = min(p2);
            boolean collides = !(p1max < p2min || p1min > p2max);
            if (!collides) {
                return false;
            }
        }
        return true;
    }

    public float[] projection(Vector3 axis) {
        return new float[]{
                getGlobalCorner(false, false, false).scalar(axis),
                getGlobalCorner(false, false, true).scalar(axis),
                getGlobalCorner(false, true, false).scalar(axis),
                getGlobalCorner(false, true, true).scalar(axis),
                getGlobalCorner(true, false, false).scalar(axis),
                getGlobalCorner(true, false, true).scalar(axis),
                getGlobalCorner(true, true, false).scalar(axis),
                getGlobalCorner(true, true, true).scalar(axis),
        };
    }

    private float max(float... nums) {
        float max = Float.MIN_VALUE;
        for (float num : nums) {
            max = Math.max(max, num);
        }
        return max;
    }

    private float min(float... nums) {
        float min = Float.MAX_VALUE;
        for (float num : nums) {
            min = Math.min(min, num);
        }
        return min;
    }

    public Vector3 getCorner(boolean x, boolean y, boolean z) {
        return getSize().inverse(x, y, z);
    }

    public Vector3 getGlobalCorner(boolean x, boolean y, boolean z) {
        return translateToGlobal(getCorner(x, y, z));
    }

    public boolean mediumBoxCollideCheck(BoxHitbox box) {
        return checkOtherLocalPointInMe(box, box.getCorner(false, false, false)) ||
                checkOtherLocalPointInMe(box, box.getCorner(false, false, true)) ||
                checkOtherLocalPointInMe(box, box.getCorner(false, true, false)) ||
                checkOtherLocalPointInMe(box, box.getCorner(false, true, true)) ||
                checkOtherLocalPointInMe(box, box.getCorner(true, false, false)) ||
                checkOtherLocalPointInMe(box, box.getCorner(true, false, true)) ||
                checkOtherLocalPointInMe(box, box.getCorner(true, true, false)) ||
                checkOtherLocalPointInMe(box, box.getCorner(true, true, true));
    }

    private boolean checkOtherLocalPointInMe(BoxHitbox box, Vector3 point) {
        Vector3 gp = box.translateToGlobal(point);
        Vector3 mlp = this.translateToLocal(gp);
        return isMyLocalPointInside(mlp);
    }

    private boolean isMyLocalPointInside(Vector3 point) {
        Vector3 localPoint = this.translateToLocal(point);
        float x = localPoint.getX();
        float y = localPoint.getY();
        float z = localPoint.getZ();
        float sx = getSize().getX();
        float sy = getSize().getY();
        float sz = getSize().getZ();
        return (-sx <= x && x <= sx) &&
                (-sy <= y && y <= sy) &&
                (-sz <= z && z <= sz);
    }

    @Override
    public boolean contains(Vector3 point) {
        return false;
    }

    @Override
    public float getRawRadius() {
        float xh = getSize().getX();
        float yh = getSize().getY();
        float zh = getSize().getZ();
        return (float) Math.sqrt(xh * xh + yh * yh + zh * zh);
    }

    @Override
    public Vector3 getCenter() {
        if (isStatic) {
            return position;
        } else {
            return orientation.getCenter();
        }
    }

    @Override
    public Vector3 getSize() {
        if (isStatic) {
            return size;
        } else {
            return orientation.getSize();
        }
    }

    @Override
    public Quaternion getRotation() {
        if (isStatic) {
            return rotation;
        } else {
            return orientation.getRotation();
        }
    }

    @Override
    public boolean isStatic() {
        return isStatic;
    }
}
