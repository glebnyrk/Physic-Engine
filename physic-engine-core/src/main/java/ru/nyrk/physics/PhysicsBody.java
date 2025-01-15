package ru.nyrk.physics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nyrk.hitboxes.AABB;
import ru.nyrk.BVH.BVHChild;
import ru.nyrk.BVH.BVHTreePart;
import ru.nyrk.hitboxes.HitBox;
import ru.nyrk.hitboxes.MeshHitBox;
import ru.nyrk.maths.Quaternion;
import ru.nyrk.maths.Vector3;
import ru.nyrk.orientation_providers.OrientationReturn;
import ru.nyrk.physics.plugins.FallingPower;
import ru.nyrk.physics.plugins.WindagePlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Объект описывающий физический объект на сцене
 */
public class PhysicsBody implements BVHChild, OrientationReturn {
    private final ImpulseMap impulse = new ImpulseMap(Vector3.ZERO);
    private final List<HitBox> hitBoxes;
    private float mass;
    private Vector3 position;
    private Quaternion rotation;
    private Vector3 size;
    private final Vector3 delta_size;
    private PhysicsScene myScene;
    private boolean isStatic;
    private BVHTreePart parent;
    private boolean deleteOrder = false;
    /**
     * Полный конструктор. Используйте PhysicsBodyBuilder
     *
     * @param pos   - позиция
     * @param rot   - поворот
     * @param sizeN - размер
     * @param h     - массив хитбоксов. Должны быть привязаны к объекту
     * @param isS   - создать объект изначально статичным
     * @param sizeD - динамический размер
     */
    PhysicsBody(Vector3 pos,
                Quaternion rot,
                Vector3 sizeN,
                List<HitBox> h,
                boolean isS,
                ImpulseMap impulse,
                Vector3 sizeD) {
        position = pos;
        rotation = rot;
        size = sizeN;
        mass = 1;
        delta_size = sizeD;
        hitBoxes = h;
        isStatic = isS;
        this.impulse.add(impulse);
    }

    /**
     * @return - масса объекта в килограммах
     */
    public float getMass() {
        return mass;
    }

    /**
     * Изменяет массу объекта.
     *
     * @param mass - новая масса объекта в килограммах
     */
    public void setMass(float mass) {
        this.mass = mass;
    }

    /**
     * Даёт возможность заблокировать объект в пространстве. Лишает физики, но позволяет перемещать с помощью move() и safeMove()
     *
     * @param isStatic true - заблокировать; false - разблокировать
     */
    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    /**
     * @return сцена на которой объект находится
     */
    public @Nullable PhysicsScene getScene() {
        return myScene;
    }

    /**
     * @param newScene новая сцена объекта
     */
    public void setScene(@Nullable PhysicsScene newScene) {
        myScene = newScene;
    }

    /**
     * @return массив хитбоксов объекта
     */
    public @Nullable List<HitBox> getHitBoxes() {
        return hitBoxes;
    }

    /**
     * @return центр вращения, изменения размера. Позиция.
     */
    public @NotNull Vector3 getCenter() {
        return position;
    }

    /**
     * @return кватернион вращения
     */
    public Quaternion getRotation() {
        return rotation;
    }

    /**
     * @return получение глобального размера (может измениться)
     */
    public @NotNull Vector3 getSize() {
        return new Vector3(size.getX() * delta_size.getX(),
                size.getY() * delta_size.getY(),
                size.getZ() * delta_size.getZ());
    }

    /**
     * Установить статичный размер (может измениться)
     *
     * @param size - новый полуразмер
     */
    public void setSize(@NotNull Vector3 size) {
        this.size = size;
    }

    /**
     * Проверяет, сталкиваются ли два объекта
     *
     * @param otherBody объект
     */
    public final Vector3 collidesWith(@NotNull PhysicsBody otherBody) {
        List<HitBox> hitBoxes1 = otherBody.getHitBoxes();
        Vector3 _r = Vector3.ZERO;
        if (otherBody == this) {
            return _r;
        }
        for (HitBox own : hitBoxes) {
            for (HitBox other : hitBoxes1) {
                Vector3 collide = own.collideMeta(other);
                if (collide != null) {
                    _r = _r.add(collide);
                }
            }
        }
        return _r;
    }

    /**
     * Проверяет сразу всю коллекцию на столкновения
     *
     * @param other - коллекция
     * @return - объект с которым произошло столкновение (возвращает null если коллизий не произошло)
     */
    public @Nullable
    final Vector3 collidesWith(@NotNull Collection<PhysicsBody> other) {
        Vector3 _r = Vector3.ZERO;
        for (PhysicsBody physicsBody : other) {
            _r = _r.add(collidesWith(physicsBody));
        }
        return _r;
    }

    /**
     * Радиус сферы предварительной проверки коллизий
     */
    public final float rawRadius() {
        if (hitBoxes == null || hitBoxes.isEmpty()) {
            return 0;
        }
        float maxDistance = 0;
        for (HitBox hitbox : hitBoxes) {
            float currentDistance = hitbox.getRawRadius() + hitbox.getCenter().distance(position);
            if (currentDistance > maxDistance) {
                maxDistance = currentDistance;
            }
        }
        return maxDistance;
    }

    /**
     * Обрабатывает impulses для симуляции физики. Используются плагины физики PhysicsPlugin
     *
     * @param deltaTime - задержка с прошлого вызова в секундах
     */
    public void physicsTick(float deltaTime) {
        if (isStatic) {
            return;
        }
        motionTick(deltaTime);
        new FallingPower().process(this, deltaTime);
        new WindagePlugin().process(this, deltaTime);
    }

    /**
     * Обрабатывает импульсы и отражает их в случае столкновений
     *
     * @param deltaTime - задержка с прошлого вызова в секундах
     */
    public void motionTick(float deltaTime) {
        safeMove(getVelocity(),rotation,deltaTime);
    }

    /**
     * Добавляет импульс
     *
     * @param v вектор импульса
     */
    public void addImpulse(Vector3 v) {
        impulse.add(v);
    }
    public Quaternion getRotationForce(){
        return impulse.rotationForce();
    }
    /**
     * Умножение импульса на число
     *
     * @param b - число
     */
    public void multiplyImpulse(float b) {
        impulse.mul(b);
    }

    /**
     * Получение общего (сумма всех импульсов на углах) импульса
     *
     * @return вектор импульса
     */
    public @NotNull Vector3 getImpulse() {
        return impulse.getImpulse();
    }

    /**
     * Сдвигает и поворачивает объект (без учета коллизий)
     *
     * @param velocity  - скорость с которой надо сдвинуть
     * @param rot       - кватернион вращения на который нужно повернуть
     * @param deltaTime - задержка с прошлого вызова / время для вычисления дальности сдвига и поворота
     */
    public void move(Vector3 velocity, Quaternion rot, float deltaTime) {
        velocity = velocity.mul(deltaTime);
        Quaternion newRotation = new Quaternion(rot.getR() * deltaTime, rot.getI(), rot.getJ(), rot.getK());
        position = position.add(velocity);
        rotation = rotation.rotate(newRotation);
    }

    /**
     * Сдвигает и поворачивает объект (с учетом коллизий)
     *
     * @param velocity  - скорость с которой надо сдвинуть
     * @param rot       - кватернион вращения на который нужно повернуть
     * @param deltaTime - задержка с прошлого вызова / время для вычисления дальности сдвига и поворота
     * @return объект с которым произошло столкновение во время движения (если столкновения не произошло, возвращает null)
     */
    public void safeMove(Vector3 velocity, Quaternion rot, float deltaTime) {
        move(velocity, rot, deltaTime);
        List<PhysicsBody> raws = myScene.getRaws(this);
        boolean collision = false;
        for (PhysicsBody physicsBody : raws) {
            Vector3 mtv = collidesWith(physicsBody);
            if (!mtv.equals(Vector3.ZERO)) {
                position = position.add(mtv);
                collision = true;
            }
        }
        if (collision) {
            impulse.mul(0);
        }
    }
    public @NotNull AABB getAABB() {
        return new AABB(List.of(this));
    }

    @Override
    public Vector3 getMin() {
        float rr = rawRadius();
        return getCenter().sub(new Vector3(rr, rr, rr));
    }
    @Override
    public Vector3 getMax() {
        float rr = rawRadius();
        return getCenter().add(new Vector3(rr, rr, rr));
    }
    @Override
    public int getDeep() {
        return 0;
    }
    private static final ArrayList<PhysicsBody> emptyBodies = new ArrayList<>();
    @Override
    public List<PhysicsBody> rawCollides(@NotNull PhysicsBody other) {
        return other.getAABB().collide(getAABB()) ? List.of(this) : emptyBodies;
    }

    @Override
    public String toString(int indent) {
        String spaces = spaces(indent + 1);
        String spaces1 = spaces(indent);
        return spaces1 + "PhysicsBody(" + getAABBVolume() +"){\n" +
                spaces + "Position: " + position + ";\n"
                + spaces1 + "}";
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Vector3 getVelocity() {
        return getImpulse().mul(1/mass);
    }

    private String spaces(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("|   ");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return toString(0);
    }

    @Override
    public float getAABBVolume() {
        return getAABB().getVolume();
    }

    @Override
    public BVHTreePart getParent() {
        return parent;
    }

    @Override
    public void setParent(BVHTreePart newParent) {
        this.parent = newParent;
    }

    @Override
    public void yieldDelete() {
        deleteOrder = true;
    }

    @Override
    public boolean isInDeleteOrder() {
        return deleteOrder;
    }
}
