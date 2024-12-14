package ru.nyrk;

public class BoxHitbox extends Hitbox {
    private final boolean isStatic;
    private final OrientationReturn orientation;
    private Vector3 position;
    private Vector3 size;
    private Quaternion rotation;
    private float rawRadius;

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
        return clearBoxCollideCheck((BoxHitbox) other);
//        if (!rawCollideCheck(other)) {
//            return false;
//        } else {
//            if (other instanceof BoxHitbox box) {
//            }
//            else {
//                return false;
//            }
//        }
    }

    /**
     * Полностью проверяет коллизии с other
     *
     * @param other
     * @return
     */
    public boolean clearBoxCollideCheck(BoxHitbox other) {
        Quaternion xAxis = new Quaternion(90,new Vector3(1,0,0));
        Quaternion yAxis = new Quaternion(90,new Vector3(0,1,0));
        Quaternion zAxis = new Quaternion(90,new Vector3(0,0,1));
        Vector3 y = new Vector3(getRotation().rotate(yAxis));
        Vector3 x = new Vector3(getRotation().rotate(xAxis));
        Vector3 z = new Vector3(getRotation().rotate(zAxis));
        Vector3 x1 = new Vector3(other.getRotation().rotate(xAxis));
        Vector3 y1 = new Vector3(other.getRotation().rotate(yAxis));
        Vector3 z1 = new Vector3(other.getRotation().rotate(zAxis));

        Vector3[] axes = new Vector3[]{
                x,
                y,
                z,
                x1,
                y1,
                z1,
                x.mul(x1),
                x.mul(y1),
                x.mul(z1),
                y.mul(x1),
                y.mul(y1),
                y.mul(z1),
                z.mul(x1),
                z.mul(y1),
                z.mul(z1)
        };
        for (int i = 0; i < axes.length; i++) {
            axes[i] = axes[i].normalize();
        }
        for (Vector3 axis : axes) {
            float[] p1 = projection(axis);
            float[] p2 = other.projection(axis);
            float p1min = min(p1);
            float p1max = max(p1);
            float p2max = max(p2);
            float p2min = min(p2);
            boolean collides = overlap(p1min, p1max, p2min, p2max) >= 0;
            if (!collides) {
                return false;
            }
        }
        return true;
    }

    private float overlap(float min1, float max1, float min2, float max2) {
        return Math.min(max1, max2) - Math.max(min1, min2);
    }

    /**
     * проекция углов на ось axis
     *
     * @return
     */
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

    /**
     * Получение локального угла
     */
    public Vector3 getCorner(boolean x, boolean y, boolean z) {
        return getSize().inverse(x, y, z);
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
