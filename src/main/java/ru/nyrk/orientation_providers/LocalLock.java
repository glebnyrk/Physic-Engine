package ru.nyrk.orientation_providers;

import ru.nyrk.maths.Quaternion;
import ru.nyrk.maths.Vector3;

/**
 * Позволяет очень удобно зафиксировать ориентацию локально другой
 */
public class LocalLock implements OrientationReturn {
    private final OrientationReturn orientation;
    private final OrientationReturn localOrientation;

    /**
     * @param original ориентация относительно которой производятся вычисления (глобальная)
     * @param local    локальная ориентация
     */
    public LocalLock(OrientationReturn original, OrientationReturn local) {
        orientation = original;
        localOrientation = local;
    }

    @Override
    public Vector3 getCenter() {
        return orientation.translateToGlobal(localOrientation.getCenter());
    }

    @Override
    public Vector3 getSize() {
        Vector3 size = orientation.getSize();
        Vector3 localsize = localOrientation.getSize();
        return new Vector3(size.getX() * localsize.getX(), size.getY() * localsize.getY(), size.getZ() * localsize.getZ());
    }

    @Override
    public Quaternion getRotation() {
        return orientation.getRotation().rotate(localOrientation.getRotation());
    }

}
