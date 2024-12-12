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