package ru.nyrk.physics;

import ru.nyrk.maths.Vector3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class OverWorld extends PhysicsScene {
    private static final Vector3 GRAVITYCONSTANT = new Vector3(0, -32, 0); //ускорение в свободном падении в майн крафте

    public OverWorld() {
        ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);
        scheduler.scheduleAtFixedRate(this, 0, TARGET_DELAY, TimeUnit.MILLISECONDS);
    }

    @Override
    public Vector3 getGravityConstant() {
        return GRAVITYCONSTANT;
    }

    @Override
    public synchronized void physicsTick(float delta) {
        List<PhysicsBody> objects = getObjects();
        for (PhysicsBody object : objects) {
            object.physicsTick(delta);
        }
    }

    @Override
    public void update(float delta) {

    }
}
