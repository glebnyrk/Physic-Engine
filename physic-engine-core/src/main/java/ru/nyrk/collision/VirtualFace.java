package ru.nyrk.collision;

import ru.nyrk.maths.Vector3;

class VirtualFace {
    final int a,b,c;
    Vector3 normal;
    VirtualFace(int a, int b, int c, Vector3 normal) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.normal = normal;
    }

    public int getA() {
        return a;
    }
    public int getB() {
        return b;
    }
    public int getC() {
        return c;
    }
    public Vector3 getNormal() {
        return normal;
    }
    public static Vector3 normal(Vector3 a, Vector3 b, Vector3 c) {
        Vector3 ab = b.sub(a);
        Vector3 ac = c.sub(a);
        return ab.mul(ac);
    }
}
