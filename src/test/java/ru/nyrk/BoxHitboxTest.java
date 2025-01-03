package ru.nyrk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nyrk.hitboxes.BoxHitbox;
import ru.nyrk.maths.Quaternion;
import ru.nyrk.maths.Vector3;

import static org.junit.jupiter.api.Assertions.*;

class BoxHitboxTest {
    private float r() {
        return (float) Math.random();
    }

    @Test
    void testBoxHitboxInner() {
        BoxHitbox boxHitbox = new BoxHitbox(new Vector3(0, 0, 0), new Vector3(10, 10, 10));
        BoxHitbox boxHitbox2 = new BoxHitbox(new Vector3(0, 0, 0), new Vector3(1, 1, 1));
        assertTrue(boxHitbox.collidesWith(boxHitbox2));
    }

    @Test
    void testBoxHitboxOuter() {
        BoxHitbox boxHitbox2 = new BoxHitbox(new Vector3(0, 0, 0), new Vector3(10, 10, 10));
        BoxHitbox boxHitbox = new BoxHitbox(new Vector3(1, 0, 0), new Vector3(1, 1, 1));
        assertTrue(boxHitbox.collidesWith(boxHitbox2));
    }

    @Test
    void testBoxHitboxTouches() {
        BoxHitbox boxHitbox = new BoxHitbox(new Vector3(2, 0, 0));
        BoxHitbox boxHitbox2 = new BoxHitbox(new Vector3(0, 0, 0));
        assertTrue(boxHitbox.collidesWith(boxHitbox2));
    }

    @Test
    void testBoxHitboxDoesNotTouch() {
        BoxHitbox boxHitbox = new BoxHitbox(new Vector3(0, 2.1f, 0));
        BoxHitbox boxHitbox2 = new BoxHitbox(new Vector3(0, 0, 0));
        assertFalse(boxHitbox.collidesWith(boxHitbox2));
    }

    @Test
    void testBoxHitboxCorner() {
        BoxHitbox boxHitbox = new BoxHitbox(new Vector3(2, 1.5f, -0.5f));
        BoxHitbox boxHitbox2 = new BoxHitbox(Vector3.ZERO, Vector3.ONE, new Quaternion(0.92388f, 0.167017f, 0.334033f, 0.083508f));
        assertTrue(boxHitbox.collidesWith(boxHitbox2));
    }

    @Test
    void edgeCollideTrue() {
        BoxHitbox boxHitbox = new BoxHitbox(Vector3.ZERO, Vector3.ONE);
        BoxHitbox boxHitbox2 = new BoxHitbox(new Vector3(0, 1.5f, 2f), Vector3.ONE, new Quaternion(0.853553f, 0.353553f, -0.146447f, 0.353553f));
        assertTrue(boxHitbox2.collidesWith(boxHitbox));
    }

    @Test
    void veryFar() {
        BoxHitbox boxHitbox = new BoxHitbox(Vector3.ZERO, Vector3.ONE);
        BoxHitbox boxHitbox2 = new BoxHitbox(new Vector3(0, 150, 2f), Vector3.ONE, new Quaternion(0.853553f, 0.353553f, -0.146447f, 0.353553f));
        assertFalse(boxHitbox.collidesWith(boxHitbox2));
    }

    @Test
    void SizeTest() {
        BoxHitbox boxHitbox = new BoxHitbox(Vector3.ZERO, new Vector3(1.49128f, 1.08751f, 1));
        BoxHitbox boxHitbox2 = new BoxHitbox(new Vector3(2.322f, 0, 0), Vector3.ONE, new Quaternion(0.994276f, 0.050195f, -0.083639f, 0.043579f));
        assertTrue(boxHitbox.collidesWith(boxHitbox2));
    }

    @Test
    void SizeTest2() {
        BoxHitbox boxHitbox = new BoxHitbox(Vector3.ZERO, new Vector3(0.480445f, 0.350363f, 0.32217f));
        BoxHitbox boxHitbox2 = new BoxHitbox(new Vector3(1.73114f, 0, 0), Vector3.ONE, new Quaternion(0.994276f, 0.050195f, -0.083639f, 0.043579f));
        assertFalse(boxHitbox.collidesWith(boxHitbox2));
    }

    @Test
    void test3() {
        BoxHitbox boxHitbox = new BoxHitbox(Vector3.ZERO);
        BoxHitbox boxHitbox2 = new BoxHitbox(new Vector3(-1.78711f, 0, 1.7833f), Vector3.ONE, new Quaternion(45, new Vector3(0, 1, 0)));
        Assertions.assertFalse(boxHitbox.collidesWith(boxHitbox2));
    }

    @Test
    void tooFar() {
        for (int i = 0; i < 1000; i++) {
            BoxHitbox hitbox = new BoxHitbox(Vector3.ZERO, new Vector3(1, 1, 1), new Quaternion(1, 0, 0, 0));
            float x = r() * 5;
            float y = r() * 5;
            float z = r() * 5;
            BoxHitbox hitbox2 = new BoxHitbox(new Vector3(x, y, z), new Vector3(1, 1, 1), new Quaternion(r(), new Vector3(r(), r(), r())));
            if (hitbox.collidesWith(hitbox2)) {
                Assertions.assertTrue(hitbox2.getCenter().distance(hitbox.getCenter()) - 0.01 < hitbox.getRawRadius() + hitbox2.getRawRadius());
            }
        }
    }

    @Test
    void falseTest() {
        BoxHitbox boxHitbox = new BoxHitbox(Vector3.ZERO, new Vector3(1,1.24721f,2.0214f), new Quaternion(0.94974f,-0.136983f,0.26654f,0.090478f));
        BoxHitbox boxHitbox2 = new BoxHitbox(new Vector3(-1.73903f, -0.274576f, 0.911562f), new Vector3(0.799887f,0.765903f,0.547655f), new Quaternion(0.686036f,-0.480437f,0.546002f,-0.020421f));
        Assertions.assertFalse(boxHitbox.collidesWith(boxHitbox2));
    }
}