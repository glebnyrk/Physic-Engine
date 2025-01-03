package ru.nyrk.physics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nyrk.hitboxes.AABB;
import ru.nyrk.BVH.BVHChild;
import ru.nyrk.BVH.BVHTreePart;
import ru.nyrk.hitboxes.Hitbox;
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
public class PhysicsBody extends OrientationReturn implements BVHChild {
    private final ImpulseMap impulse = new ImpulseMap(Vector3.ZERO, Vector3.ZERO, Vector3.ZERO, Vector3.ZERO, Vector3.ZERO, Vector3.ZERO, Vector3.ZERO, Vector3.ZERO);
    private final Hitbox[] hitBoxes;
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
                Hitbox[] h,
                boolean isS,
                Vector3 impulseFFF,
                Vector3 impulseFFT,
                Vector3 impulseFTF,
                Vector3 impulseFTT,
                Vector3 impulseTFF,
                Vector3 impulseTFT,
                Vector3 impulseTTF,
                Vector3 impulseTTT,
                Vector3 sizeD) {
        position = pos;
        rotation = rot;
        size = sizeN;
        mass = 1;
        delta_size = sizeD;
        hitBoxes = h;
        isStatic = isS;
        impulse.setCornerImpulse(ImpulseCorner.FFF, impulseFFF);
        impulse.setCornerImpulse(ImpulseCorner.FFT, impulseFFT);
        impulse.setCornerImpulse(ImpulseCorner.FTF, impulseFTF);
        impulse.setCornerImpulse(ImpulseCorner.FTT, impulseFTT);
        impulse.setCornerImpulse(ImpulseCorner.TFF, impulseTFF);
        impulse.setCornerImpulse(ImpulseCorner.TFT, impulseTFT);
        impulse.setCornerImpulse(ImpulseCorner.TTF, impulseTTF);
        impulse.setCornerImpulse(ImpulseCorner.TTT, impulseTTT);
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
    public @Nullable Hitbox[] getHitBoxes() {
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
    public final boolean collidesWith(@NotNull PhysicsBody otherBody) {
        Hitbox[] hitBoxes1 = otherBody.getHitBoxes();
        for (Hitbox own : hitBoxes) {
            for (Hitbox other : hitBoxes1) {
                boolean collide = own.collidesWith(other);
                if (collide) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Проверяет сразу всю коллекцию на столкновения
     *
     * @param other - коллекция
     * @return - объект с которым произошло столкновение (возвращает null если коллизий не произошло)
     */
    public @Nullable
    final PhysicsBody collidesWith(@NotNull Collection<PhysicsBody> other) {
        for (PhysicsBody physicsBody : other) {
            if (collidesWith(physicsBody)) {
                return physicsBody;
            }
        }
        return null;
    }

    /**
     * Радиус сферы предварительной проверки коллизий
     */
    public final float rawRadius() {
        if (hitBoxes == null || hitBoxes.length == 0) {
            return 0;
        }
        float maxDistance = 0;
        for (Hitbox hitbox : hitBoxes) {
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
        boolean collided = safeMove(getImpulse().mul(1f / getMass()), Quaternion.ZERO, deltaTime) != null;
//        if (collided) {
//            multiplyImpulse(-1);
//        }
    }

    /**
     * Добавляет импульс
     *
     * @param v вектор импульса
     */
    public void addImpulse(Vector3 v) {
        impulse.add(v);
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
     * Получение импульса конкретного угла
     *
     * @param corner угол импульс которого нужно получить
     */
    public @NotNull Vector3 getImpulse(ImpulseCorner corner) {
        return impulse.getCornerImpulse(corner);
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
    public @Nullable PhysicsBody safeMove(Vector3 velocity, Quaternion rot, float deltaTime) {
        PhysicsBody _r = null;
        for (int i = 0; i < PhysicsScene.MOVE_QUALITY; i++) {
            velocity = velocity.mul(0.5f);
            move(velocity, rot, deltaTime);
            PhysicsBody collidesWith = collidesWith(myScene.getRaws(this));
            if (collidesWith != null) {
                _r = collidesWith;
            }
            boolean collides = collidesWith != null;
            if (collides) {
                move(velocity, rot, -deltaTime);
            }
        }
        return _r;
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
