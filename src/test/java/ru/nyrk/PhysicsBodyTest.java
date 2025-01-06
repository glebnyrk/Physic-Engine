package ru.nyrk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nyrk.hitboxes.BoxHitBox;
import ru.nyrk.hitboxes.MeshHitBox;
import ru.nyrk.maths.Vector3;
import ru.nyrk.orientation_providers.LocalLock;
import ru.nyrk.orientation_providers.StaticOrientation;
import ru.nyrk.physics.PhysicsBody;
import ru.nyrk.physics.PhysicsBodyBuilder;

class PhysicsBodyTest {
    private float r(){
        return (float) Math.random() * 10;
    }
    float sqrt = (float) Math.sqrt(3);
    PhysicsBody cube(){
        MeshHitBox[] hitBoxes = new MeshHitBox[1];
        PhysicsBody body = new PhysicsBodyBuilder().setSize(Vector3.ONE).setPos(new Vector3(r(),r(),r())).setHitBoxes(hitBoxes).createPhysicsBody();
        hitBoxes[0] = new BoxHitBox(new LocalLock(body, new StaticOrientation()));
        return body;
    }
    @Test
    void aabbTest(){
        for (int i = 0; i < 10; i++) {
            Assertions.assertEquals(cube().getAABB().getVolume(), Math.pow(sqrt*2,3), 0.0001f);
        }
    }
    @Test
    void rawRadiusTest(){
        for (int i = 0; i < 10; i++) {
            Assertions.assertEquals(cube().rawRadius(), sqrt,0.01f);
        }
    }
}