package ru.nyrk.physics;

import org.w3c.dom.ls.LSOutput;
import ru.nyrk.BVH.BVHTreePart;
import ru.nyrk.maths.Vector3;

import java.util.ArrayList;
import java.util.List;

public abstract class PhysicsScene implements Runnable {
    public static final long TARGET_DELAY = 10;
    private final ArrayList<PhysicsBody> objects = new ArrayList<>();
    private long last_call = System.nanoTime();
    private boolean running = false;
    private final BVHTreePart tree = new BVHTreePart();
    /**
     * Направление и сила гравитации (ха-ха, ну сделай измерение в котором всё будет падать вверх, это же смешно)
     */
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

    /**
     * Проверяет, работает ли симуляция на сцене
     *
     * @return
     */
    public synchronized boolean isWorking() {
        return running;
    }

    /**
     * Ставит симуляцию на паузу
     */
    public synchronized void pause() {
        running = false;
    }

    /**
     * Снимает симуляцию с паузы
     */
    public synchronized void resume() {
        running = true;
    }

    /**
     * Метод для обработки физики
     *
     * @param delta
     */
    public abstract void physicsTick(float delta);

    /**
     * Метод для всякой без полезной фигни
     *
     * @param delta
     */
    public abstract void update(float delta);

    public synchronized final void addObject(PhysicsBody object) {
        if (object.getScene() != null) {
            object.getScene().removeObject(object);
        }
        object.setScene(this);
        tree.insert(object);
        objects.add(object);
    }

    /**
     * Удаляет объект со сцены
     *
     * @param object
     */
    public synchronized final void removeObject(PhysicsBody object) {
        objects.remove(object);
        tree.parentalDelete(object);
    }

    /**
     * Возвращает список объектов на сцене
     */
    public synchronized final List<PhysicsBody> getObjects() {
        return objects;
    }
    public synchronized final PhysicsBody getObject(int index) {return getObjects().get(index);}
    /**
     * Возвращает список объектов которые могли бы пересекаться с object
     */
    public synchronized List<PhysicsBody> getRaws(PhysicsBody object) {
        return tree.rawCollides(object);
    }
}
