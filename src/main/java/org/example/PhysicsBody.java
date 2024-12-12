package org.example;

import org.example.physics_plugins.FallingPower;

import java.util.Collection;

public class PhysicsBody extends OrientationReturn {
    private final ImpulseMap impulse = new ImpulseMap(Vector3.ZERO, Vector3.ZERO, Vector3.ZERO, Vector3.ZERO, Vector3.ZERO, Vector3.ZERO, Vector3.ZERO, Vector3.ZERO);
    private float mass;

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    private Vector3 position;
    private Quaternion rotation;
    private Vector3 size;

    public void setSize(Vector3 size) {
        this.size = size;
    }

    private Vector3 delta_size;
    private final Hitbox[] hitBoxes;
    private PhysicsScene myScene;
    private boolean isStatic;

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

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

    public PhysicsScene getScene() {
        return myScene;
    }

    public void setScene(PhysicsScene newScene) {
        myScene = newScene;
    }

    public Hitbox[] getHitBoxes() {
        return hitBoxes;
    }

    public Vector3 getCenter() {
        return position;
    }

    public Quaternion getRotation() {
        return rotation;
    }

    public Vector3 getSize() {
        return new Vector3(size.getX() * delta_size.getX(),
                size.getY() * delta_size.getY(),
                size.getZ() * delta_size.getZ());
    }

    public final boolean collidesWith(PhysicsBody other) {
        for (Hitbox owns : hitBoxes) {
            Hitbox[] hitBoxes1 = other.getHitBoxes();
            for (Hitbox others : hitBoxes1) {
                boolean collide = owns.collidesWith(others);
                if (collide) {
                    return true;
                }
            }
        }
        return false;
    }

    public final PhysicsBody collidesWith(Collection<PhysicsBody> other) {
        for (PhysicsBody physicsBody : other) {
            if (collidesWith(physicsBody)) {
                return physicsBody;
            }
        }
        return null;
    }

    public final float rawRadius() {
        float maxDistance = 0;
        for (Hitbox hitbox : hitBoxes) {
            float currentDistance = hitbox.getRawRadius() + hitbox.getCenter().distance(position);
            if (currentDistance > maxDistance) {
                maxDistance = currentDistance;
            }
        }
        return maxDistance;
    }

    public void physicsTick(float deltaTime) {
        if (isStatic) {
            return;
        }
        motionTick(deltaTime);

        new FallingPower().process(this, deltaTime);
    }

    public void motionTick(float delta) {
        boolean collided = safeMove(getImpulse().mul(1f / getMass()), Quaternion.ZERO, delta) != null;
        if (collided) {
            multiplyImpulse(-1);
        }
    }

    public void addImpulse(Vector3 v) {
        impulse.add(v);
    }

    public void multiplyImpulse(float b) {
        impulse.mul(b);
    }

    public Vector3 getImpulse() {
        return impulse.getImpulse();
    }

    public Vector3 getImpulse(ImpulseCorner corner) {
        return impulse.getCornerImpulse(corner);
    }

    public void move(Vector3 velocity, Quaternion rot, float deltaTime) {
        velocity = velocity.mul(deltaTime);
        Quaternion newRotation = new Quaternion(rot.getR() * deltaTime, rot.getI(), rot.getJ(), rot.getK());
        position = position.add(velocity);
        rotation = rotation.rotate(newRotation);
    }

    public PhysicsBody safeMove(Vector3 velocity, Quaternion rot, float deltaTime) {
        PhysicsBody _r;
        for (int i = 0; i < PhysicsScene.MOVE_QUALITY; i++) {
            velocity = velocity.mul(0.5f);
            move(velocity, rot, deltaTime);
            _r = collidesWith(myScene.getRaws(this));
            boolean collides = _r != null;
            if (collides) {
                move(velocity.mul(-1), rot, deltaTime);
            }
        }
        return null;
    }
}
