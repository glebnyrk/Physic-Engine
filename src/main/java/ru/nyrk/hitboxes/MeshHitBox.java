package ru.nyrk.hitboxes;

import ru.nyrk.maths.Vector3;

import java.util.ArrayList;
import java.util.List;

public interface MeshHitBox extends HitBox{
    /**
     * Быстро проверяет возможность коллизии между ним и other
     */
    public default boolean rawCollideCheck(MeshHitBox other) {
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
    public float getRawRadius();

    public default List<Vector3> getGlobalPoints(){
        List<Vector3> localPoints = getLocalPoints();
        List<Vector3> globalPoints = new ArrayList<>(localPoints.size());
        for (int i = 0; i < localPoints.size(); i++) {
            globalPoints.set(i,translateToGlobal(localPoints.get(i)));
        }
        return globalPoints;
    }
    public List<Vector3> getLocalPoints();

    public default List<Float> projection(Vector3 axis){
        axis = axis.normalize();
        List<Float> projections = new ArrayList<>(8);
        List<Vector3> points = getGlobalPoints();
        for (int i = 0; i < 8; i++) {
            projections.set(i, points.get(i).scalar(axis));
        }
        return projections;
    }

    public default Vector3 support(Vector3 axis) {
        axis = axis.normalize();
        List<Vector3> points = getGlobalPoints();
        float max = Float.NEGATIVE_INFINITY;
        Vector3 maxPoint = Vector3.ZERO;
        for (Vector3 point : points) {
            float scalar = point.scalar(axis);
            if (scalar > max) {
                max = scalar;
                maxPoint = point;
            }
        }
        return maxPoint;
    }
}
