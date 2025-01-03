package ru.nyrk.physics.plugins;

import ru.nyrk.physics.PhysicsBody;

public interface PhysicsPlugin {
    void process(PhysicsBody body, float deltaTime);
}
