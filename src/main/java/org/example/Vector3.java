package org.example;

import java.util.zip.ZipError;

public class Vector3 {
    final private float x;
    final private float y;
    final private float z;
    final private float length;
    final private float squared_length;
    public static final Vector3 ZERO = new Vector3(0.0f, 0.0f, 0.0f);
    public static final Vector3 ONE = new Vector3(1f, 1f, 1f);
    public static final Vector3 X = new Vector3(1f, 0.0f, 0.0f);
    public static final Vector3 MINUSX = new Vector3(-1f, 0.0f, 0.0f);
    public static final Vector3 Y = new Vector3(0f, 1f, 0.0f);
    public static final Vector3 MINUSY = new Vector3(0f, -1f, 0.0f);
    public static final Vector3 Z = new Vector3(0f, 0f, 1f);
    public static final Vector3 MINUSZ = new Vector3(0f, 0f, -1f);

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        squared_length = x * x + y * y + z * z;
        length = (float) Math.sqrt(squared_length);

    }

    public Vector3(Quaternion quaternion) {
        if (quaternion.getI() == 0 && quaternion.getJ() == 0 && quaternion.getK() == 0) {
            x = 1f;
            y = 0f;
            z = 0f;
            length = 1;
            squared_length = 1f;
        } else {
            x = quaternion.getI();
            y = quaternion.getJ();
            z = quaternion.getK();
            squared_length = x * x + y * y + z * z;
            length = (float) Math.sqrt(squared_length);
        }
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

    float length() {
        return length;
    }

    float lengthSquared() {
        return squared_length;
    }

    Vector3 normalize() {
        if (length > 0) {
            return new Vector3(x / length, y / length, z / length);
        }
        else {
            return new Vector3(1f, 0,0);
        }
    }

    Vector3 add(Vector3 b) {
        return new Vector3(x + b.x, y + b.y, z + b.z);
    }

    Vector3 sub(Vector3 b) {
        return new Vector3(x - b.x, y - b.y, z - b.z);
    }

    Vector3 mul(float b) {
        return new Vector3(x * b, y * b, z * b);
    }

    float scalar(Vector3 b) {
        return x * b.getX() + y * b.getY() + z * b.getZ();
    }

    Vector3 mul(Vector3 b) {
        return new Vector3(y * b.getZ() - z * b.getY(),
                z * b.getX() - x * b.getZ(),
                x * b.getY() - y * b.getX());
    }

    float distance(Vector3 b) {
        return (float) this.sub(b).length();
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
}
