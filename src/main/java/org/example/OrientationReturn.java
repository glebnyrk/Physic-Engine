package org.example;

/**
 * Фундамент для иерархии привязок объектов друг к другу
 */
public abstract class OrientationReturn {
    /**
     * Получение центра
     */
    abstract Vector3 getCenter();

    /**
     * Получение поворота
     * @return
     */
    abstract Vector3 getSize();

    /**
     * Получение поворота вращения
     * @return
     */
    abstract Quaternion getRotation();

    /**
     * Перевести глобальную точку в локальную
     */
    public final Vector3 translateToLocal(Vector3 point){
        Vector3 lpd = getCenter().sub(point);
        return new Vector3(getRotation().back().mul(new Quaternion(lpd)).mul(getRotation()));
    }

    /**
     * Перевести локальную точку в глобальную
     * @param point
     * @return
     */
    public final Vector3 translateToGlobal(Vector3 point){
        Vector3 gcp = new Vector3(getRotation().mul(new Quaternion(point)).mul(getRotation().back()));
        return getCenter().add(gcp);
    }
}
