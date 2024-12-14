package ru.nyrk;

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
     * TOFIX
     */
    public final Vector3 translateToLocal(Vector3 point){
        point = getCenter().sub(point);
//        point = new Vector3(new Quaternion(point).rotate(getRotation().conjugate()));
//        point = new Vector3(point.getX() / getSize().getX(), point.getY() / getSize().getY(), point.getZ() / getSize().getZ());
        return point;
    }

    /**
     * Перевести локальную точку в глобальную
     */
    public final Vector3 translateToGlobal(Vector3 p){
        Quaternion q = getRotation();
        Vector3 t = getCenter();
        Vector3 size = getSize();
        return new Vector3(q.mul(new Quaternion(new Vector3(p.getX() * size.getX(), p.getY() * size.getY(), p.getZ() * size.getZ())))).add(t);
    }
}
