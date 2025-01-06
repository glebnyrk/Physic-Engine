package ru.nyrk.hitboxes;

import ru.nyrk.BVH.AABLike;
import ru.nyrk.collision.GJK;
import ru.nyrk.maths.Vector3;
import ru.nyrk.orientation_providers.OrientationReturn;

public interface HitBox extends AABLike, OrientationReturn {
    /**
     * Проверяет коллизию себя с other
     */
    public default boolean collidesWith(HitBox other){
        return GJK.gjk(this,other) != null;
    }

    /**
     * Быстро проверяет возможность коллизии между ним и other
     */
    public default boolean rawCollideCheck(HitBox other) {
        float radius_sum = other.getRawRadius() + getRawRadius();
        float distance_sum = other.getCenter().distance(this.getCenter());
        return distance_sum <= radius_sum;
    }

    /**
     * Возвращает радиус для условия предварительной проверки коллизии
     *
     * @return
     */
    public float getRawRadius();

    public Vector3 support(Vector3 axis);
}
