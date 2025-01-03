package ru.nyrk.physics.plugins;

import ru.nyrk.physics.PhysicsBody;

public class WindagePlugin implements PhysicsPlugin{
    @Override
    public void process(PhysicsBody body, float deltaTime) {
         body.multiplyImpulse((float) Math.pow(2f/3f, deltaTime));
    }
}
