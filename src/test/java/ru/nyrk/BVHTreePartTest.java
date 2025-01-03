package ru.nyrk;

import org.junit.jupiter.api.Test;
import ru.nyrk.BVH.BVHTreePart;
import ru.nyrk.hitboxes.BoxHitbox;
import ru.nyrk.hitboxes.Hitbox;
import ru.nyrk.maths.Vector3;
import ru.nyrk.orientation_providers.LocalLock;
import ru.nyrk.orientation_providers.StaticOrientation;
import ru.nyrk.physics.PhysicsBody;
import ru.nyrk.physics.PhysicsBodyBuilder;

class BVHTreePartTest {
    private float r() {
        return ((float) Math.random() * 20);
    }

    @Test
    void toStringTest() {
        BVHTreePart bvh = new BVHTreePart(null, null);
        PhysicsBody[] cubes = new PhysicsBody[(int) Math.pow(2, 20)];
        for (int i = 0; i < Math.pow(2, 15); i++) {
            cubes[i] = cube();
        }
        System.out.println("cubes initialized");
        for (int i = 0; i < Math.pow(2, 15); i++) {
            bvh.insert(cubes[i]);
        }
        System.out.println("cubes inserted");
        System.out.println(bvh.getDeep());
        System.out.println(bvh.getCount());
        for (int i = 0; i < Math.pow(2, 15) - 4; i++) {
            bvh.parentalDelete(cubes[i]);
        }
        System.out.println("cubes deleted");
        bvh.serviceDeleteOrder();
        System.out.println("collector worked");
        System.out.println(bvh);
        System.out.println(bvh.getList());
        bvh.rebuild();
        System.out.println("bvh rebuilt");
        System.out.println(bvh);
    }

    private PhysicsBody cube() {
        Hitbox[] hitBoxes = new Hitbox[1];
        float z = 0.57735026918962576450914878f;
        PhysicsBody body = new PhysicsBodyBuilder().setSize(new Vector3(z, z, z)).setPos(new Vector3(r(), r(), r())).setHitBoxes(hitBoxes).createPhysicsBody();
        hitBoxes[0] = new BoxHitbox(new LocalLock(body, new StaticOrientation()));
        return body;
    }
}