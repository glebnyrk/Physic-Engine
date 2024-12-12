package org.example;

import java.util.ArrayList;
import java.util.Date;

public abstract class PhysicsScene implements Runnable {
    public static final long TARGET_DELAY = 50;
    public static final int MOVE_QUALITY = 8;
    private final ArrayList<PhysicsBody> objects = new ArrayList<>();
    private long last_call = System.nanoTime();
    private boolean running = false;

    public abstract Vector3 getGravityConstant();

    public final void run() {
        long time = System.nanoTime();
        long delta = time - last_call;
        last_call = time;
        if (isWorking()) {
            physicsTick((float) delta / 1000000000f);
            update((float) delta / 1000000000f);
        }
    }

    public synchronized boolean isWorking() {
        return running;
    }

    public synchronized void pause() {
        running = false;
    }

    public synchronized void resume() {
        running = true;
    }

    public abstract void physicsTick(float delta);

    public abstract void update(float delta);

    public synchronized final void addObject(PhysicsBody object) {
        if (object.getScene() != null) {
            object.getScene().removeObject(object);
        }
        object.setScene(this);
        objects.add(object);
    }

    public synchronized final void removeObject(PhysicsBody object) {
        objects.remove(object);
    }

    public synchronized final ArrayList<PhysicsBody> getObjects() {
        return objects;
    }

    public synchronized ArrayList<PhysicsBody> getRaws(PhysicsBody object) {
        ArrayList<PhysicsBody> _r = new ArrayList<>();
        for (PhysicsBody other : objects) {
            if (other != object) {
                float objectRawRadius = object.rawRadius();
                float otherRawRadius = other.rawRadius();
                if (objectRawRadius + otherRawRadius > object.getCenter().distance(other.getCenter())) {
                    _r.add(other);
                }
            }
        }
        return _r;
    }
}
