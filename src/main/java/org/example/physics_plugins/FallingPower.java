package org.example.physics_plugins;

import org.example.PhysicsBody;
import org.example.PhysicsScene;
import org.example.Vector3;

public class FallingPower implements PhysicsPlugin{
    @Override
    public void process(PhysicsBody body, float deltaTime) {
        PhysicsScene scene = body.getScene();
        Vector3 gravityConstant = scene.getGravityConstant();
        body.addImpulse(gravityConstant.mul(body.getMass()).mul(deltaTime));
    }
}
