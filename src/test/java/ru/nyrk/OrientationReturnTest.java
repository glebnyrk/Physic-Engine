package ru.nyrk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrientationReturnTest {
    private float r() {
        return (float) Math.random();
    }

    @Test
    public void testLocalToGlobal() {
        for (int i = 0; i < 1000; i++) {
            BoxHitbox hitbox = new BoxHitbox(new Vector3(r(), r(), r()), new Vector3(r(), r(), r()), new Quaternion(r(), r(), r(), r()));
            Vector3 l = new Vector3(r(), r(), r());
            assertTrue(hitbox.translateToLocal(hitbox.translateToGlobal(l)).equals(l, 0.01f));
        }
    }

    @Test
    public void testDistance() {
        for (int i = 0; i < 100000; i++) {
            BoxHitbox boxHitbox = new BoxHitbox(new Vector3(0, 0, 0), new Vector3(1, 1, 1), new Quaternion(r(), r(), r(), r()));
            Vector3 l = new Vector3(1, 1, 1);
            Assertions.assertEquals(boxHitbox.translateToGlobal(l).length(), Math.sqrt(3), 0.01f);
        }
    }
}