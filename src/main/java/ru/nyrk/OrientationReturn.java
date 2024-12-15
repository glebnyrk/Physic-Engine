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
     *
     * @return
     */
    abstract Vector3 getSize();

    /**
     * Получение поворота вращения
     *
     * @return
     */
    abstract Quaternion getRotation();

    /**
     * Перевести глобальную точку в локальную
     */
    public final Vector3 translateToLocal(Vector3 point) {
        Vector3 position = point.sub(getCenter());
        Vector3 rotated = new Vector3(new Quaternion(position).rotate(getRotation().conjugate()));
        Vector3 sized = new Vector3(rotated.getX() / getSize().getX(), rotated.getY() / getSize().getY(), rotated.getZ() / getSize().getZ());
        return sized;
    }

    /**
     * Перевести локальную точку в глобальную
     */
    public final Vector3 translateToGlobal(Vector3 p) {
        Vector3 sized = new Vector3(p.getX() * getSize().getX(), p.getY() * getSize().getY(), p.getZ() * getSize().getZ());
        Vector3 rotated = new Vector3(new Quaternion(sized).rotate(getRotation()));
        Vector3 centred = rotated.add(getCenter());
        return centred;
    }
}
