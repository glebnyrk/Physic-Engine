package ru.nyrk.collision;

import ru.nyrk.maths.Vector3;

public class GJKReturnPojo {
    public GJKReturnPojo() {

    }
    public GJKReturnPojo(Simplex simplex, Vector3 vector) {
        this.simplex = simplex;
        this.direction = vector;
    }
    public boolean collides = false;
    public Vector3 direction = Vector3.X;
    public Simplex simplex = new Simplex();
}
