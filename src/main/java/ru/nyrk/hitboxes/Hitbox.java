package ru.nyrk.hitboxes;

import ru.nyrk.BVH.AABLike;
import ru.nyrk.maths.Quaternion;
import ru.nyrk.maths.Vector3;
import ru.nyrk.orientation_providers.OrientationReturn;

public abstract class Hitbox extends OrientationReturn implements AABLike {
    /**
     * Проверяет коллизию себя с other
     */
    public abstract boolean collidesWith(Hitbox other);

    /**
     * Быстро проверяет возможность коллизии между ним и other
     */
    public final boolean rawCollideCheck(Hitbox other) {
        float radius_sum = other.getRawRadius() + getRawRadius();
        float dictation_sum = other.getCenter().distance(this.getCenter());
        return dictation_sum <= radius_sum;
    }

    /**
     * Проверяет, лежит ли точка внутри хитбокса
     *
     * @param point
     * @return
     */
    public abstract boolean contains(Vector3 point);

    /**
     * Возвращает радиус для условия предварительной проверки коллизии
     *
     * @return
     */
    public abstract float getRawRadius();

    public abstract Vector3 getCenter();

    public abstract Vector3 getSize();

    public abstract Quaternion getRotation();

    public abstract Vector3[] getNormals(boolean includeRepeating);

    public abstract Vector3[] getPoints();

    public abstract float[] projection(Vector3 axis);
    public final Vector3 support(Vector3 axis) {
        axis = axis.normalize();
        Vector3[] points = getPoints();
        float max = Float.NEGATIVE_INFINITY;
        Vector3 maxPoint = Vector3.ZERO;
        for (Vector3 point : points) {
            float scalar = point.scalar(axis);
            if (scalar > max){
                max = scalar;
                maxPoint = point;
            }
        }
        return maxPoint;
    }
}
