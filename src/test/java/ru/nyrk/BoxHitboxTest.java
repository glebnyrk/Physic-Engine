package ru.nyrk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxHitboxTest {
    @Test
    void testBoxHitboxInner() {
        BoxHitbox boxHitbox = new BoxHitbox(new Vector3(0, 0, 0), new Vector3(10, 10, 10));
        BoxHitbox boxHitbox2 = new BoxHitbox(new Vector3(0, 0, 0), new Vector3(1, 1, 1));
        assertTrue(boxHitbox.collidesWith(boxHitbox2));
    }

    @Test
    void testBoxHitboxOuter() {
        BoxHitbox boxHitbox2 = new BoxHitbox(new Vector3(0, 0, 0), new Vector3(10, 10, 10));
        BoxHitbox boxHitbox = new BoxHitbox(new Vector3(0, 0, 0), new Vector3(1, 1, 1));
        assertTrue(boxHitbox.collidesWith(boxHitbox2));
    }

    @Test
    void testBoxHitboxTouches() {
        BoxHitbox boxHitbox = new BoxHitbox(new Vector3(2, 2, 0));
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
    void testBoxHitboxCornerFalse() {
        BoxHitbox boxHitbox = new BoxHitbox(Vector3.ZERO, Vector3.ONE, new Quaternion(-0.382683f,0,0,0.92388f).normalize());
        BoxHitbox boxHitbox2 = new BoxHitbox(new Vector3(2f,0,2f), Vector3.ONE);
        assertFalse(boxHitbox2.collidesWith(boxHitbox));
    }
    @Test
    void testBoxHitboxCornerTrue() {
        BoxHitbox boxHitbox = new BoxHitbox(Vector3.ZERO, Vector3.ONE, new Quaternion(-0.308156f,-0.307276f,-0.214804f,0.874346f));
        BoxHitbox boxHitbox2 = new BoxHitbox(new Vector3(-1.8f,-1.8f,1), Vector3.ONE, new Quaternion(0.968314f,-0.208427f,0.095427f,0.099096f));
        assertFalse(boxHitbox2.collidesWith(boxHitbox));
    }
    @Test
    void edgeCollideTrue(){
        BoxHitbox boxHitbox = new BoxHitbox(Vector3.ZERO, Vector3.ONE);
        BoxHitbox boxHitbox2 = new BoxHitbox(new Vector3(0,1.5f,2f), Vector3.ONE, new Quaternion(0.853553f,0.353553f,-0.146447f,0.353553f));
        assertTrue(boxHitbox2.collidesWith(boxHitbox));
    }
    @Test
    void veryFar(){
        BoxHitbox boxHitbox = new BoxHitbox(Vector3.ZERO, Vector3.ONE);
        BoxHitbox boxHitbox2 = new BoxHitbox(new Vector3(0,150,2f), Vector3.ONE, new Quaternion(0.853553f,0.353553f,-0.146447f,0.353553f));
        assertFalse(boxHitbox.collidesWith(boxHitbox2));
    }
    @Test
    void SizeTest(){
        BoxHitbox boxHitbox = new BoxHitbox(Vector3.ZERO, new Vector3(1.49128f,1.08751f,1));
        BoxHitbox boxHitbox2 = new BoxHitbox(new Vector3(2.322f,0,0), Vector3.ONE,new Quaternion(0.994276f,0.050195f,-0.083639f,0.043579f));
        assertTrue(boxHitbox.collidesWith(boxHitbox2));
    }
    @Test
    void SizeTest2(){
        BoxHitbox boxHitbox = new BoxHitbox(Vector3.ZERO, new Vector3(0.480445f,0.350363f,0.32217f));
        BoxHitbox boxHitbox2 = new BoxHitbox(new Vector3(1.73114f,0,0), Vector3.ONE,new Quaternion(0.994276f,0.050195f,-0.083639f,0.043579f));
        assertFalse(boxHitbox.collidesWith(boxHitbox2));
    }
}