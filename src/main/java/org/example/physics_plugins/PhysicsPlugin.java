package org.example.physics_plugins;

import org.example.PhysicsBody;

public interface PhysicsPlugin {
    void process(PhysicsBody body, float deltaTime);
}
