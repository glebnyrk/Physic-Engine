package ru.nyrk.orientation_providers;

import ru.nyrk.maths.Quaternion;
import ru.nyrk.maths.Vector3;

/**
 * Фундамент для иерархии привязок объектов друг к другу
 */
public interface OrientationReturn {
    /**
     * Получение центра
     */
    public Vector3 getCenter();

    /**
     * Получение поворота
     *
     * @return
     */
    public Vector3 getSize();

    /**
     * Получение поворота вращения
     *
     * @return
     */
    public Quaternion getRotation();

    /**
     * Перевести глобальную точку в локальную
     */
    public default Vector3 translateToLocal(Vector3 point) {
        Vector3 position = point.sub(getCenter());
        Vector3 rotated = new Vector3(new Quaternion(position).fullRotation(getRotation().conjugate()));
        Vector3 sized = new Vector3(rotated.getX() / getSize().getX(), rotated.getY() / getSize().getY(), rotated.getZ() / getSize().getZ());
        return sized;
    }

    /**
     * Перевести локальную точку в глобальную
     */
    public default Vector3 translateToGlobal(Vector3 p) {
        Vector3 sized = new Vector3(p.getX() * getSize().getX(), p.getY() * getSize().getY(), p.getZ() * getSize().getZ());
        Vector3 rotated = new Vector3(new Quaternion(sized).fullRotation(getRotation()));
        Vector3 centred = rotated.add(getCenter());
        return centred;
    }
}
