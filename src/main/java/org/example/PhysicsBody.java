package org.example;

import java.util.Collection;

public class PhysicsBody extends OrientationReturn {
    private final VelocityCluster velocity = new VelocityCluster(Vector3.ZERO,Vector3.ZERO,Vector3.ZERO,Vector3.ZERO,Vector3.ZERO,Vector3.ZERO,Vector3.ZERO,Vector3.ZERO);
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
    void setVelocity(VelocityCorner corner) {

    }
    PhysicsBody(Vector3 pos,
                Quaternion rot,
                Vector3 sizeN,
                Hitbox[] h,
                boolean isS,
                Vector3 velocityFFF,
                Vector3 velocityFFT,
                Vector3 velocityFTF,
                Vector3 velocityFTT,
                Vector3 velocityTFF,
                Vector3 velocityTFT,
                Vector3 velocityTTF,
                Vector3 velocityTTT,
                Vector3 sizeD) {
        position = pos;
        rotation = rot;
        size = sizeN;
        mass = 1;
        delta_size = sizeD;
        hitBoxes = new Hitbox[]{new BoxHitbox(this)};
        isStatic = isS;
        velocity.setCornerVelocity(VelocityCorner.FFF,velocityFFF);
        velocity.setCornerVelocity(VelocityCorner.FFT,velocityFFT);
        velocity.setCornerVelocity(VelocityCorner.FTF,velocityFTF);
        velocity.setCornerVelocity(VelocityCorner.FTT,velocityFTT);
        velocity.setCornerVelocity(VelocityCorner.TFF,velocityTFF);
        velocity.setCornerVelocity(VelocityCorner.TFT,velocityTFT);
        velocity.setCornerVelocity(VelocityCorner.TTF,velocityTTF);
        velocity.setCornerVelocity(VelocityCorner.TTT,velocityTTT);
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

        gravityPlugin(deltaTime);
    }

    private void gravityPlugin(float deltaTime) {
        addVelocity(new Vector3(0, -9.80665f, 0).mul(mass).mul(deltaTime));
    }
    public void motionTick(float delta) {
        boolean collided = safeMove(getVelocity(), Quaternion.ZERO, delta) != null;
        if (collided) {

        }
    }

    public void addVelocity(Vector3 v) {
        v = v.mul(1f / mass);
        velocity.add(velocity);
    }
    public void multiplyVelocity(float b){
        velocity.mul(b);
    }
    public Vector3 getVelocity() {
        return velocity.getVelocity();
    }
    public Vector3 getVelocityCorner(VelocityCorner corner) {
        return velocity.getCornerVelocity(corner);
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
