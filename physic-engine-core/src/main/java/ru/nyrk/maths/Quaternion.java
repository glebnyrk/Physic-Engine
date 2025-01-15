package ru.nyrk.maths;

import ru.nyrk.tools.MakesNewObject;

import java.util.Objects;

/**
 * Кватернион
 */
public class Quaternion {
    final public static Quaternion ZERO = new Quaternion(1, 0, 0, 0);
    final private static float rad = (float) Math.PI / 180;
    final private float r;
    final private float i;
    final private float j;
    final private float k;
    final private float squaredLength;
    final private float length;

    /**
     * Создание кватерниона из мнимых и вещественной составляющих
     *
     * @param r - вещественная составляющая
     * @param i - первая мнимая
     * @param j - вторая мнимая
     * @param k - третья мнимая
     */
    public Quaternion(float r, float i, float j, float k) {
        this.r = r;
        this.i = i;
        this.j = j;
        this.k = k;
        squaredLength = r * r + i * i + j * j + k * k;
        length = (float) Math.sqrt(squaredLength);
    }

    /**
     * Готовый для вращения кватернион
     *
     * @param angle  в градусах
     * @param vector rotation axis
     */
    public Quaternion(float angle, Vector3 vector) {
        // Переводим угол в радианы
        angle = angle * rad;

        // Нормализуем вектор
        vector = vector.normalize();

        // Вычисляем компоненты кватерниона
        float halfSin = (float) Math.sin(angle / 2);
        float halfCos = (float) Math.cos(angle / 2);

        r = halfCos;
        i = vector.getX() * halfSin;
        j = vector.getY() * halfSin;
        k = vector.getZ() * halfSin;

        // Устанавливаем длину (нормализованному кватерниону длина равна 1)
        squaredLength = 1.0f;
        length = 1.0f;
    }

    /**
     * Создание кватерниона вращения из вектора.
     *
     * @param vector
     */
    public Quaternion(Vector3 vector) {
        r = 0;
        i = vector.getX();
        j = vector.getY();
        k = vector.getZ();
        squaredLength = vector.lengthSquared();
        length = vector.length();
    }

    /**
     * Получение второй мнимой части
     *
     * @return
     */
    public float getJ() {
        return j;
    }

    /**
     * Получение первой мнимой части
     *
     * @return
     */
    public float getI() {
        return i;
    }

    /**
     * Получение
     */
    public float getR() {
        return r;
    }

    /**
     * Получение третьей мнимой части
     *
     * @return
     */
    public float getK() {
        return k;
    }

    @MakesNewObject
    public Quaternion add(Quaternion b) {
        return new Quaternion(r + b.getR(), i + b.getI(), j + b.getJ(), k + b.getK());
    }

    @MakesNewObject
    public Quaternion sub(Quaternion b) {
        return new Quaternion(r - b.getR(), i - b.getI(), j - b.getJ(), k - b.getK());
    }

    @MakesNewObject
    public Quaternion mul(Quaternion b) {
        return new Quaternion(
                r * b.getR() - i * b.getI() - j * b.getJ() - k * b.getK(),
                r * b.getI() + i * b.getR() + j * b.getK() - k * b.getJ(),
                r * b.getJ() - i * b.getK() + j * b.getR() + k * b.getI(),
                r * b.getK() + i * b.getJ() - j * b.getI() + k * b.getR()
        );
    }

    @MakesNewObject
    public Quaternion div(Quaternion b) {
        return b.back().mul(this);
    }

    @MakesNewObject
    public Quaternion conjugate() {
        return new Quaternion(r, -i, -j, -k);
    }

    public float squaredLength() {
        return squaredLength;
    }

    public float length() {
        return length;
    }

    @MakesNewObject
    public Quaternion normalize() {
        if (length == 0 || length == 1) {
            return this;
        }
        return new Quaternion(r / length, i / length, j / length, k / length);
    }

    @MakesNewObject
    public Quaternion back() {
        if (length == 0) {
            return Quaternion.ZERO.back();
        }
        return conjugate().mul(new Quaternion(1 / squaredLength, 0, 0, 0));
    }

    @MakesNewObject
    public Quaternion fullRotation(Quaternion b) {
        return b.mul(this).mul(b.back());
//        return this.mul(b);
    }
    @MakesNewObject
    public Quaternion rotate(Quaternion b) {
        return this.mul(b);
    }
    @MakesNewObject
    public Quaternion rotateX(float angle) {
        return this.rotate(new Quaternion(angle, new Vector3(1, 0, 0)));
    }

    @MakesNewObject
    public Quaternion rotateY(float angle) {
        return this.rotate(new Quaternion(angle, new Vector3(0, 1, 0)));
    }

    @MakesNewObject
    public Quaternion rotateZ(float angle) {
        return this.rotate(new Quaternion(angle, new Vector3(0, 0, 1)));
    }

    @MakesNewObject
    public Quaternion eulerRotation(Vector3 angles) {
        return this.rotateX(angles.getX()).rotateY(angles.getY()).rotateZ(angles.getZ());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Quaternion that)) return false;
        return Float.compare(getR(), that.getR()) == 0 && Float.compare(getI(), that.getI()) == 0 && Float.compare(getJ(), that.getJ()) == 0 && Float.compare(getK(), that.getK()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getR(), getI(), getJ(), getK());
    }

    public String toString() {
        return "Q(" + r + ", " + i + ", " + j + ", " + k + ")";
    }

    public boolean equals(Quaternion q) {
        return q.r == r && q.i == i && q.j == j && q.k == k;
    }

    public Matrix4f quaternionToMatrix() {
        Matrix4f mat = new Matrix4f();
        float w = r;
        float x = i;
        float y = j;
        float z = k;

        mat.m[0][0] = (float) 1 - 2 * y * y - 2 * z * z;
        mat.m[0][1] = 2 * x * y - 2 * w * z;
        mat.m[0][2] = 2 * x * z + 2 * w * y;

        mat.m[1][0] = 2 * x * y + 2 * w * z;
        mat.m[1][1] = 1 - 2 * x * x - 2 * z * z;
        mat.m[1][2] = 2 * y * z - 2 * w * x;

        mat.m[2][0] = 2 * x * z - 2 * w * y;
        mat.m[2][1] = 2 * y * z + 2 * w * x;
        mat.m[2][2] = 1 - 2 * x * x - 2 * y * y;

        mat.m[3][3] = 1.0f;  // Единичная матрица с масштабом 1
        return mat;
    }
}
