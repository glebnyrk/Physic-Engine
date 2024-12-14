package ru.nyrk.physics_plugins;

import ru.nyrk.PhysicsBody;

public interface PhysicsPlugin {
    void process(PhysicsBody body, float deltaTime);
}
