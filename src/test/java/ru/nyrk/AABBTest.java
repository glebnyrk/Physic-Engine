package ru.nyrk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AABBTest {
    @Test
    public void normalizeTest() {
        Assertions.assertTrue(new AABB(new Vector3(5, 6, 7), new Vector3(-5, -7, -2)).equals(new AABB(new Vector3(-5, -7, -2), new Vector3(5, 6, 7))));
        Assertions.assertTrue(new AABB(new Vector3(5, 6, 7), new Vector3(-5, 7, -2)).equals(new AABB(new Vector3(-5, 6, -2), new Vector3(5, 7, 7))));
    }

    @Test
    public void volumeTest() {
        Assertions.assertTrue(new AABB(new Vector3(5, 6, 7), new Vector3(-5, -7, -2)).volume() == 1170);
        Assertions.assertTrue(new AABB(new Vector3(3.5f, 6.7f, 28.64f), new Vector3(0.5f, 0.7f, 0.64f)).volume() == 3 * 6 * 28);
    }

    @Test
    public void insideTest() {
        {
            AABB aabb = new AABB(new Vector3(-1, -1, -1), new Vector3(1, 1, 1));
            Vector3 a = new Vector3(0.9f, 0.9f, 0.9f);
            Assertions.assertTrue(aabb.isInside(a));
        }
        {
            AABB aabb = new AABB(new Vector3(-1, -1, -1), new Vector3(1, 1, 1));
            Vector3 a = new Vector3(1f, 1f, 1f);
            Assertions.assertFalse(aabb.isInside(a));
        }
        {
            AABB aabb = new AABB(new Vector3(-2, -2, -2), new Vector3(1, 1, 1));
            Vector3 a = new Vector3(-1f, -1f, -1f);
            Assertions.assertTrue(aabb.isInside(a));
        }
        {
            AABB aabb = new AABB(new Vector3(-2, -2, -2), new Vector3(1, 1, 1));
            Vector3 a = new Vector3(3f, 2f, 7f);
            Assertions.assertFalse(aabb.isInside(a));
        }
    }

    @Test
    public void collideTest() {
        {
            AABB a = new AABB(new Vector3(-1, -1, -1), new Vector3(1, 1, 1));
            AABB b = new AABB(new Vector3(-2, -2, -2), new Vector3(1, 1, 1));
            Assertions.assertTrue(a.collide(b));
        }
        {
            AABB a = new AABB(new Vector3(0, 0, 0), new Vector3(-3, -3, 3));
            AABB b = new AABB(new Vector3(-1, -1, -2), new Vector3(1, 1, 1));
            Assertions.assertTrue(a.collide(b));
        }
        {
            AABB a = new AABB(new Vector3(0, 0, 0), new Vector3(-3, -3, 3));
            AABB b = new AABB(new Vector3(-1, -1, -2), new Vector3(1, 1, 1));
            Assertions.assertTrue(a.collide(b));
        }
        {
            AABB a = new AABB(new Vector3(-1, -1, 1), new Vector3(-3, -3, 3));
            AABB b = new AABB(new Vector3(-1, -1, -1), new Vector3(1, 1, 1));
            Assertions.assertFalse(a.collide(b));
        }
        {
            AABB a = new AABB(new Vector3(-1, -1, 1), new Vector3(1, 1, 1));
            AABB b = new AABB(new Vector3(-1, -10, -1), new Vector3(1, -8, 1));
            Assertions.assertFalse(a.collide(b));
        }
    }
}
