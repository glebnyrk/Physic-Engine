package ru.nyrk.maths;

public class Vector3 {
    public static final Vector3 ZERO = new Vector3(0.0f, 0.0f, 0.0f);
    public static final Vector3 ONE = new Vector3(1f, 1f, 1f);
    public static final Vector3 X = new Vector3(1f, 0.0f, 0.0f);
    public static final Vector3 NEGATIVE_X = new Vector3(-1f, 0.0f, 0.0f);
    public static final Vector3 Y = new Vector3(0f, 1f, 0.0f);
    public static final Vector3 NEGATIVE_Y = new Vector3(0f, -1f, 0.0f);
    public static final Vector3 Z = new Vector3(0f, 0f, 1f);
    public static final Vector3 NEGATIVE_Z = new Vector3(0f, 0f, -1f);
    public static final Vector3 INFINITY = new Vector3(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
    public static final Vector3 NEGATIVE_INFINITY = new Vector3(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
    final private float x;
    final private float y;
    final private float z;
    final private float length;
    final private float squared_length;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        squared_length = x * x + y * y + z * z;
        length = (float) Math.sqrt(squared_length);

    }

    public Vector3(Quaternion quaternion) {
        x = quaternion.getI();
        y = quaternion.getJ();
        z = quaternion.getK();
        squared_length = x * x + y * y + z * z;
        length = (float) Math.sqrt(squared_length);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float length() {
        return length;
    }

    public float lengthSquared() {
        return squared_length;
    }

    public Vector3 normalize() {
        if (length > 0) {
            return length == 1 ? this : new Vector3(x / length, y / length, z / length);
        } else {
            return this;
        }
    }

    public Vector3 add(Vector3 b) {
        return new Vector3(x + b.x, y + b.y, z + b.z);
    }

    public Vector3 sub(Vector3 b) {
        return new Vector3(x - b.x, y - b.y, z - b.z);
    }

    public Vector3 mul(float b) {
        return new Vector3(x * b, y * b, z * b);
    }

    public float scalar(Vector3 b) {
        return x * b.getX() + y * b.getY() + z * b.getZ();
    }

    public Vector3 mul(Vector3 b) {
        return new Vector3(y * b.getZ() - z * b.getY(),
                z * b.getX() - x * b.getZ(),
                x * b.getY() - y * b.getX());
    }

    public float distance(Vector3 b) {
        return this.sub(b).length();
    }

    public Vector3 inverse(boolean xi, boolean yi, boolean zi) {
        return new Vector3(xi ? x : -x, yi ? y : -y, zi ? z : -z);
    }

    public Vector3 inverse() {
        return inverse(true, true, true);
    }

    public String toString() {
        return "V3(" + x + ", " + y + ", " + z + ")";
    }

    public boolean equals(Vector3 b) {
        return x == b.getX() && y == b.getY() && z == b.getZ();
    }

    public boolean equals(Vector3 b, float delta) {
        return (x <= b.getX() + delta && x >= b.getX() - delta) &&
                (y <= b.getY() + delta && y >= b.getY() - delta) &&
                (z <= b.getZ() + delta && z >= b.getZ() - delta);
    }
    public Vector3 min(Vector3 b) {
        float xmin = Math.min(x, b.getX());
        float ymin = Math.min(y, b.getY());
        float zmin = Math.min(z, b.getZ());
        return new Vector3(xmin, ymin, zmin);
    }
    public Vector3 max(Vector3 b) {
        if (this.equals(b)) {
            return this;
        }
        float xmax = Math.max(x, b.getX());
        float ymax = Math.max(y, b.getY());
        float zmax = Math.max(z, b.getZ());
        if (xmax == x && ymax == y && zmax == z) {
            return this;
        } else if (xmax == b.getX() && ymax == b.getY() && zmax == b.getZ()) {
            return b;
        }
        return new Vector3(xmax, ymax, zmax);
    }
    public boolean moreThan(Vector3 b) {
        return b.x < x && b.y < y && b.z < z;
    }

    public boolean lessThan(Vector3 b) {
        return b.x > x && b.y > y && b.z > z;
    }
}
