package ru.nyrk;

/**
 * Позволяет очень удобно зафиксировать ориентацию локально другой
 */
public class LocalLock extends OrientationReturn{
    private final OrientationReturn orientation;
    private final OrientationReturn localOrientation;

    /**
     * @param original ориентация относительно которой производятся вычисления (глобальная)
     * @param local локальная ориентация
     */
    LocalLock(OrientationReturn original, OrientationReturn local) {
        orientation = original;
        localOrientation = local;
    }

    @Override
    public Vector3 getCenter() {
        return orientation.translateToGlobal(orientation.getCenter());
    }

    @Override
    public Vector3 getSize() {
        Vector3 size = orientation.getSize();
        Vector3 localsize = localOrientation.getSize();
        return localsize.mul(size);
    }

    @Override
    public Quaternion getRotation() {
        return orientation.getRotation().rotate(localOrientation.getRotation());
    }

}
