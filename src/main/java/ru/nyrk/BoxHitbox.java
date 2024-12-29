package ru.nyrk;

import org.jetbrains.annotations.Nullable;

public class BoxHitbox extends Hitbox {
    private final boolean isStatic;
    private final OrientationReturn orientation;
    private Vector3 position;
    private Vector3 size;
    private Quaternion rotation;

    /**
     * Создание статического хитбокса
     *
     * @param position - центр
     * @param size     - полуразмер
     * @param rotation - квантерион вращения
     */
    BoxHitbox(Vector3 position, Vector3 size, Quaternion rotation) {
        this.position = position;
        this.size = size;
        this.rotation = rotation.length() == 1 ? rotation : rotation.normalize();
        this.isStatic = true;
        orientation = null;
    }

    /**
     * Создание статического хитбокса
     *
     * @param position - центр
     * @param size     - полуразмер
     */
    BoxHitbox(Vector3 position, Vector3 size) {
        this.position = position;
        this.size = size;
        this.rotation = new Quaternion(1, 0, 0, 0);
        this.isStatic = true;
        orientation = null;
    }

    /**
     * Создание статического хитбокса
     *
     * @param position - центр
     */
    BoxHitbox(Vector3 position) {
        this.position = position;
        this.size = new Vector3(1, 1, 1);
        this.rotation = new Quaternion(1, 0, 0, 0);
        this.isStatic = true;
        orientation = null;
    }

    /**
     * Создание динамичного хитбокса привязанного к значениям orientation
     */
    BoxHitbox(OrientationReturn orientation) {
        this.isStatic = false;
        this.orientation = orientation;
    }

    /**
     * Оптимально проверят коллизии с other
     */
    @Override
    public boolean collidesWith(Hitbox other) {
        return clearBoxCollideCheck(other) != null;
    }

    /**
     * Полностью проверяет коллизии с other
     *
     * @param other
     * @return
     */
    @Nullable
    public Vector3 clearBoxCollideCheck(Hitbox other) {
        Vector3[] myNormals = getNormals(true);
        Vector3[] otherNormals = other.getNormals(true);
        Vector3[] crossNormals = new Vector3[myNormals.length * otherNormals.length];
        for (int i = 0; i < myNormals.length; i++) {
            for (int j = 0; j < otherNormals.length; j++) {
                crossNormals[i + j * otherNormals.length] = myNormals[i].mul(otherNormals[j]);
            }
        }
        Vector3 minVector3 = new Vector3(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
        for (Vector3 axis : myNormals) {
            float overlap = overlaps(other, axis);
            if (overlap < 0) {
                return null;
            }
            if (overlap < minVector3.length()) {
                minVector3 = axis.mul(overlap);
            }
        }
        for (Vector3 axis : otherNormals) {
            float overlap = overlaps(other, axis);
            if (overlap < 0) {
                return null;
            }
            if (overlap < minVector3.length()) {
                minVector3 = axis.mul(overlap);
            }
        }
        for (Vector3 axis : crossNormals) {
            float overlap = overlaps(other, axis);
            if (overlap < 0) {
                return null;
            }
            if (overlap < minVector3.length()) {
                minVector3 = axis.mul(overlap);
            }
        }
        return minVector3;
    }

    private float overlaps(Hitbox other, Vector3 axis) {
        float[] p1 = projection(axis);
        float[] p2 = other.projection(axis);
        float p1min = min(p1);
        float p1max = max(p1);
        float p2max = max(p2);
        float p2min = min(p2);
        return overlap(p1min, p1max, p2min, p2max);
    }

    public Vector3[] getNormals(boolean includeInverted) {
        Quaternion xAxis = new Quaternion(new Vector3(1, 0, 0));
        Quaternion yAxis = new Quaternion(new Vector3(0, 1, 0));
        Quaternion zAxis = new Quaternion(new Vector3(0, 0, 1));
        Vector3 y = new Vector3(yAxis.rotate(getRotation()));
        Vector3 x = new Vector3(xAxis.rotate(getRotation()));
        Vector3 z = new Vector3(zAxis.rotate(getRotation()));
        if (!includeInverted) {
            return new Vector3[]{x, y, z};
        } else {
            return new Vector3[]{x, y, z, x.mul(-1), y.mul(-1), z.mul(-1)};
        }
    }

    @Override
    public Vector3[] getPoints() {
        return new Vector3[]{
                getGlobalCorner(false, false, false),
                getGlobalCorner(false, false, true),
                getGlobalCorner(false, true, false),
                getGlobalCorner(false, true, true),
                getGlobalCorner(true, false, false),
                getGlobalCorner(true, false, true),
                getGlobalCorner(true, true, false),
                getGlobalCorner(true, true, true)
        };
    }

    private float overlap(float min1, float max1, float min2, float max2) {
        return Math.min(max1 - min2, max2 - min1);
    }

    /**
     * проекция углов на ось axis
     *
     * @return
     */
    public float[] projection(Vector3 axis) {
        axis = axis.normalize();
        float[] projections = new float[8];
        Vector3[] points = getPoints();
        for (int i = 0; i < projections.length; i++) {
            projections[i] = points[i].scalar(axis);
        }
        return projections;
    }

    private float max(float... nums) {
        float max = Float.NEGATIVE_INFINITY;
        for (float num : nums) {
            max = Math.max(max, num);
        }
        return max;
    }

    private float min(float... nums) {
        float min = Float.POSITIVE_INFINITY;
        for (float num : nums) {
            min = Math.min(min, num);
        }
        return min;
    }

    /**
     * Получение локального угла
     */
    public Vector3 getCorner(boolean x, boolean y, boolean z) {
        return new Vector3(x ? 1 : -1, y ? 1 : -1, z ? 1 : -1);
    }

    /**
     * Получение локального угла
     *
     * @return
     */
    public Vector3 getCorner(ImpulseCorner corner) {
        boolean x, y, z;
        x = corner.ordinal() % 2 == 1;
        y = (corner.ordinal() / 2) % 2 == 1;
        z = (corner.ordinal() / 4) % 2 == 1;
        return getCorner(x, y, z);
    }

    /**
     * Получение глобального угла
     */
    public Vector3 getGlobalCorner(ImpulseCorner corner) {
        return translateToGlobal(getCorner(corner));
    }

    /**
     * Получение глобального угла
     */
    public Vector3 getGlobalCorner(boolean x, boolean y, boolean z) {
        return translateToGlobal(getCorner(x, y, z));
    }

    private boolean isMyLocalPointInside(Vector3 point) {
        float x = point.getX();
        float y = point.getY();
        float z = point.getZ();
        float sx = getSize().getX();
        float sy = getSize().getY();
        float sz = getSize().getZ();
        return (-sx <= x && x <= sx) &&
                (-sy <= y && y <= sy) &&
                (-sz <= z && z <= sz);
    }

    @Override
    public boolean contains(Vector3 point) {
        return isMyLocalPointInside(translateToLocal(point));
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
            return rotation.normalize();
        } else {
            return orientation.getRotation().normalize();
        }
    }

    @Override
    public boolean isStatic() {
        return isStatic;
    }
}
