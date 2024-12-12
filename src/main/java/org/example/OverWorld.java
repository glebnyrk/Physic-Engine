package org.example;

import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class OverWorld extends PhysicsScene {
    OverWorld() {
        ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);
        scheduler.scheduleAtFixedRate(this,0, TARGET_DELAY, TimeUnit.MILLISECONDS);
    }
    private static final Vector3 GRAVITYCONSTANT = new Vector3(0,-17.76859f,0); //gravitational acceleration of minecraft world
    @Override
    public Vector3 getGravityConstant() {
        return GRAVITYCONSTANT;
    }

    @Override
    public synchronized void physicsTick(float delta) {
        ArrayList<PhysicsBody> objects = getObjects();
        for (PhysicsBody object : objects) {
            object.physicsTick(delta);
        }
    }

    @Override
    public void update(float delta) {

    }
}
