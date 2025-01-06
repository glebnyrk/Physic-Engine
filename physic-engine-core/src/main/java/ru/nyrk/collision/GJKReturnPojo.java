package ru.nyrk.collision;

import ru.nyrk.maths.Vector3;

public class GJKReturnPojo {
    public boolean collides = false;
    public Vector3 direction = Vector3.X;
    public Simplex simplex = null;
}
