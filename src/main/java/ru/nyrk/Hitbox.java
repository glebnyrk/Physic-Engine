package ru.nyrk;

public abstract class Hitbox extends OrientationReturn {
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

    /**
     * Проверяет не является ли хитбокс статичным.
     *
     * @return
     */
    public abstract boolean isStatic();
}
