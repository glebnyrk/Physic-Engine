package ru.nyrk.physics_plugins;

import ru.nyrk.PhysicsBody;
import ru.nyrk.PhysicsScene;
import ru.nyrk.Vector3;

public class FallingPower implements PhysicsPlugin{
    @Override
    public void process(PhysicsBody body, float deltaTime) {
        PhysicsScene scene = body.getScene();
        assert scene != null;
        Vector3 gravityConstant = scene.getGravityConstant();
        body.addImpulse(gravityConstant.mul(body.getMass()).mul(deltaTime));
    }
}
