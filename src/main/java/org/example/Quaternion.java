package org.example;

public class Quaternion {
    final private float r;
    final private float i;
    final private float j;
    final private float k;
    final private float squaredLength;
    final private float length;
    final private static float rad = (float) Math.PI / 180;
    final public static Quaternion ZERO = new Quaternion(1,0,0,0);
    public Quaternion(float r, float i, float j, float k) {
        this.r = r;
        this.i = i;
        this.j = j;
        this.k = k;
        squaredLength = r * r + i * i + j * j + k * k;
        length = (float) Math.sqrt(squaredLength);
    }

    /**
     * initialize ready to rotate quaternion
     *
     * @param angle  in degrees
     * @param vector rotation axis
     */
    public Quaternion(float angle, Vector3 vector) {
        vector = vector.normalize();
        angle = angle * rad;
        float nr = (float) Math.cos(angle / 2);
        float ni = (float) (vector.getX() * Math.sin(angle / 2));
        float nj = (float) (vector.getY() * Math.sin(angle / 2));
        float nk = (float) (vector.getZ() * Math.sin(angle / 2));
        float nsquaredLength = nr * nr + ni * ni + nj * nj + nk * nk;
        float nlength = (float) Math.sqrt(nsquaredLength);
        r = nr / nlength;
        i = ni / nlength;
        j = nj / nlength;
        k = nk / nlength;
        squaredLength = r * r + i * i + j * j + k * k;
        length = (float) Math.sqrt(squaredLength);
    }

    public Quaternion(Vector3 vector) {
        r = 0;
        i = vector.getX();
        j = vector.getY();
        k = vector.getZ();
        squaredLength = r * r + i * i + j * j + k * k;
        length = (float) Math.sqrt(squaredLength);
    }

    public float getI() {
        return i;
    }

    public float getJ() {
        return j;
    }

    public float getR() {
        return r;
    }

    public float getK() {
        return k;
    }

    Quaternion add(Quaternion b) {
        return new Quaternion(r + b.getR(), i + b.getI(), j + b.getJ(), k + b.getK());
    }

    Quaternion sub(Quaternion b) {
        return new Quaternion(r - b.getR(), i - b.getI(), j - b.getJ(), k - b.getK());
    }

    Quaternion mul(Quaternion b) {
        return new Quaternion(
                r * b.getR() - i * b.getI() - j * b.getJ() - k * b.getK(),
                r * b.getI() + i * b.getR() + j * b.getK() - k * b.getJ(),
                r * b.getJ() - i * b.getK() + j * b.getR() + k * b.getI(),
                r * b.getK() + i * b.getJ() - j * b.getI() + k * b.getR()
        );
    }

    Quaternion div(Quaternion b) {
        return b.back().mul(this);
    }

    Quaternion conjugate() {
        return new Quaternion(r, -i, -j, -k);
    }

    float squaredLength() {
        return squaredLength;
    }

    float length() {
        return length;
    }

    Quaternion normalize() {
        return new Quaternion(r / length, i / length, j / length, k / length);
    }

    Quaternion back() {
        return new Quaternion(1 / (length() * length()), 0, 0, 0).mul(conjugate());
    }

    Quaternion rotate(Quaternion b) {
        return this.normalize().mul(b.normalize());
    }

    Quaternion rotateX(float angle) {
        return this.rotate(new Quaternion(angle, new Vector3(1, 0, 0)));
    }

    Quaternion rotateY(float angle) {
        return this.rotate(new Quaternion(angle, new Vector3(0, 1, 0)));
    }

    Quaternion rotateZ(float angle) {
        return this.rotate(new Quaternion(angle, new Vector3(0, 0, 1)));
    }

    Quaternion eulerRotation(Vector3 angles) {
        return this.rotateX(angles.getX()).rotateY(angles.getY()).rotateZ(angles.getZ());
    }

    public String toString() {
        return "Q(" + r + ", " + i + ", " + j + ", " + k + ")";
    }

}