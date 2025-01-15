package ru.nyrk.collision;

import org.junit.jupiter.api.Test;
import ru.nyrk.hitboxes.BoxHitBox;
import ru.nyrk.hitboxes.HitBox;
import ru.nyrk.maths.Vector3;

class GJKTest {
    @Test
    void test() {
        HitBox a = new BoxHitBox(Vector3.ZERO);
        HitBox b = new BoxHitBox(Vector3.X);
        System.out.println(a.collideMeta(b));
    }
}