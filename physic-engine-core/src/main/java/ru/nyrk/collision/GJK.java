package ru.nyrk.collision;

import ru.nyrk.hitboxes.HitBox;
import ru.nyrk.maths.Vector3;
import ru.nyrk.tools.MakesNewObject;

import java.util.ArrayList;
import java.util.List;

public final class GJK {
    private GJK() {
    }



    public static Simplex gjk(HitBox hA, HitBox hB) {
        Vector3 support = support(hA, hB, hB.getCenter().sub(hA.getCenter()));
        Simplex simplex = new Simplex();
        simplex.push(support);
        GJKReturnPojo collisionTest = new GJKReturnPojo();
        Vector3 direction = support.mul(-1);
        while (true) {
            support = support(hA, hB, direction);
            if (support.scalar(direction) < 0) {
                return null;
            }
            simplex.push(support);
            collisionTest = nextSimplex(simplex, direction);
            simplex = collisionTest.simplex;
            direction = collisionTest.direction.normalize();
            if (collisionTest.collides || direction.isZero()) {
                return simplex;
            }
        }
    }

    public static Vector3 support(HitBox a, HitBox b, Vector3 direction) {
        return a.support(direction).sub(b.support(direction.mul(-1)));
    }

    public static boolean isInSameDirection(Vector3 direction, Vector3 ao) {
        return direction.scalar(ao) > 0;
    }

    public static GJKReturnPojo nextSimplex(Simplex simplex, Vector3 direction) {
        return switch (simplex.size()) {
            case 2 -> line(simplex, direction);
            case 3 -> triangle(simplex, direction);
            case 4 -> tetrahedron(simplex, direction);
            default -> new GJKReturnPojo();
        };
    }

    @MakesNewObject
    public static GJKReturnPojo line(Simplex simplex, Vector3 direction) {
        GJKReturnPojo ret = new GJKReturnPojo();
        ret.simplex = simplex;
        Vector3 b = simplex.get(0);
        Vector3 a = simplex.get(1);
        Vector3 ab = b.sub(a);
        Vector3 ao = a.mul(-1);

        if (isInSameDirection(ab, ao)) {
            ret.direction = ab.mul(ao).mul(ab);  // Cross product для корректного направления
        } else {
            ret.simplex = new Simplex(a);
            ret.direction = ao;
        }
        return ret;
    }

    @MakesNewObject
    public static GJKReturnPojo triangle(Simplex simplex, Vector3 direction) {
        GJKReturnPojo ret = new GJKReturnPojo();
        ret.simplex = simplex;
        ret.direction = direction;
        ret.collides = false;
        Vector3 c = simplex.get(0);
        Vector3 b = simplex.get(1);
        Vector3 a = simplex.get(2);
        Vector3 ab = b.sub(a);
        Vector3 ac = c.sub(a);
        Vector3 ao = a.mul(-1);
        Vector3 abc = ab.mul(ac);
        if (isInSameDirection(abc.mul(ac), ao)) {
            if (isInSameDirection(ac, ao)) {
                ret.simplex = new Simplex(c, a);
            } else {
                return line(new Simplex(b, a), direction);
            }
        } else {
            if (isInSameDirection(ab.mul(abc), ao)) {
                return line(new Simplex(b, a), direction);
            } else {
                if (isInSameDirection(abc, ao)) {
                    ret.direction = abc;
                } else {
                    ret.simplex = new Simplex(b, c, a);
                    ret.direction = abc.mul(-1);
                }
            }
        }
        return ret;
    }

    @MakesNewObject
    public static GJKReturnPojo tetrahedron(Simplex simplex, Vector3 direction) {
        GJKReturnPojo ret = new GJKReturnPojo();
        ret.simplex = simplex;
        ret.direction = direction;
        ret.collides = true;
        Vector3 a = simplex.get(3);
        Vector3 b = simplex.get(2);
        Vector3 c = simplex.get(1);
        Vector3 d = simplex.get(0);

        Vector3 ab = b.sub(a);
        Vector3 ac = c.sub(a);
        Vector3 ad = d.sub(a);
        Vector3 ao = a.mul(-1);

        Vector3 abc = ab.mul(ac);
        Vector3 acd = ac.mul(ad);
        Vector3 adb = ad.mul(ab);

        if (isInSameDirection(abc, ao)) {
            return triangle(new Simplex(c, b, a), direction);
        }
        if (isInSameDirection(acd, ao)) {
            return triangle(new Simplex(d, c, a), direction);
        }
        if (isInSameDirection(adb, ao)) {
            return triangle(new Simplex(b, d, a), direction);
        }
        return ret;
    }
}