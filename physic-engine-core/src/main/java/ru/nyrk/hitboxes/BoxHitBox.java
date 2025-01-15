package ru.nyrk.hitboxes;

import ru.nyrk.maths.Quaternion;
import ru.nyrk.maths.Vector3;
import ru.nyrk.orientation_providers.OrientationReturn;
import ru.nyrk.orientation_providers.StaticOrientation;
import ru.nyrk.physics.ImpulseCorner;

import java.util.Collections;
import java.util.List;

public class BoxHitBox implements MeshHitBox {
    private final OrientationReturn orientation;
    private static final List<Vector3> points = Collections.unmodifiableList(List.of(
            new Vector3(-1, -1, -1),
            new Vector3(1, -1, -1),
            new Vector3(-1, 1, -1),
            new Vector3(1, 1, -1),
            new Vector3(-1, -1, 1),
            new Vector3(1, -1, 1),
            new Vector3(-1, 1, 1),
            new Vector3(1, 1, 1)
    ));

    /**
     * Создание статического хитбокса
     *
     * @param position - центр
     * @param size     - полуразмер
     * @param rotation - квантерион вращения
     */
    public BoxHitBox(Vector3 position, Vector3 size, Quaternion rotation) {
        orientation = new StaticOrientation(position, size, rotation);
    }

    /**
     * Создание статического хитбокса
     *
     * @param position - центр
     * @param size     - полуразмер
     */
    public BoxHitBox(Vector3 position, Vector3 size) {
        this(position, size, Quaternion.ZERO);
    }

    /**
     * Создание статического хитбокса
     *
     * @param position - центр
     */
    public BoxHitBox(Vector3 position) {
        this(position, Vector3.ONE);
    }

    /**
     * Создание динамичного хитбокса привязанного к значениям orientation
     */
    public BoxHitBox(OrientationReturn orientation) {
        this.orientation = orientation;
    }

    private float overlaps(MeshHitBox other, Vector3 axis) {
        List<Float> p1 = projection(axis);
        List<Float> p2 = other.projection(axis);
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
        Vector3 y = new Vector3(yAxis.fullRotation(getRotation()));
        Vector3 x = new Vector3(xAxis.fullRotation(getRotation()));
        Vector3 z = new Vector3(zAxis.fullRotation(getRotation()));
        if (!includeInverted) {
            return new Vector3[]{x, y, z};
        } else {
            return new Vector3[]{x, y, z, x.mul(-1), y.mul(-1), z.mul(-1)};
        }
    }

    @Override
    public List<Vector3> getLocalPoints() {
        return points;
    }

    @Override
    public int countPoints() {
        return 8;
    }

    private float overlap(float min1, float max1, float min2, float max2) {
        return Math.min(max1 - min2, max2 - min1);
    }

    private float max(List<Float> nums) {
        float max = Float.NEGATIVE_INFINITY;
        for (float num : nums) {
            max = Math.max(max, num);
        }
        return max;
    }

    private float min(List<Float> nums) {
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
        return orientation.getCenter();
    }

    @Override
    public Vector3 getSize() {
        return orientation.getSize();
    }

    @Override
    public Quaternion getRotation() {
        return orientation.getRotation().normalize();
    }
}
