package ru.nyrk.physics.plugins;

import ru.nyrk.physics.PhysicsBody;
import ru.nyrk.physics.PhysicsScene;
import ru.nyrk.maths.Vector3;

public class FallingPower implements PhysicsPlugin{
    @Override
    public void process(PhysicsBody body, float deltaTime) {
        PhysicsScene scene = body.getScene();
        assert scene != null;
        Vector3 gravityConstant = scene.getGravityConstant();
        body.addImpulse(gravityConstant.mul(body.getMass()).mul(deltaTime));
    }
}
