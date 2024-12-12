package org.example;

public class PhysicsBodyBuilder {
    private Vector3 pos = Vector3.ZERO;
    private Quaternion rot = Quaternion.ZERO;
    private Vector3 size = Vector3.ONE;
    private Hitbox[] hs = null;
    private boolean isS = false;
    private Vector3 velocityFFF = Vector3.ZERO;
    private Vector3 velocityFFT = Vector3.ZERO;
    private Vector3 velocityFTF = Vector3.ZERO;
    private Vector3 velocityFTT = Vector3.ZERO;
    private Vector3 velocityTFF = Vector3.ZERO;
    private Vector3 velocityTFT = Vector3.ZERO;
    private Vector3 velocityTTF = Vector3.ZERO;
    private Vector3 velocityTTT = Vector3.ZERO;
    private Vector3 sizeD  = Vector3.ONE;

    public PhysicsBodyBuilder setPos(Vector3 posN) {
        this.pos = posN;
        return this;
    }
    public PhysicsBodyBuilder setVelocity(Vector3 v) {
        this.velocityFFF = v;
        this.velocityFFT = v;
        this.velocityFTF = v;
        this.velocityFTT = v;
        this.velocityTFF = v;
        this.velocityTFT = v;
        this.velocityTTF = v;
        this.velocityTTT = v;
        return this;
    }
    public PhysicsBodyBuilder setRot(Quaternion rotN) {
        this.rot = rotN;
        return this;
    }

    public PhysicsBodyBuilder setSize(Vector3 sizeN) {
        this.size = sizeN;
        return this;
    }

    public PhysicsBodyBuilder setHitBoxes(Hitbox[] hsN) {
        this.hs = hsN;
        return this;
    }

    public PhysicsBodyBuilder setStatic() {
        this.isS = true;
        return this;
    }

    public PhysicsBodyBuilder setVelocityFFF(Vector3 velocityFFF) {
        this.velocityFFF = velocityFFF;
        return this;
    }

    public PhysicsBodyBuilder setVelocityFFT(Vector3 velocityFFT) {
        this.velocityFFT = velocityFFT;
        return this;
    }

    public PhysicsBodyBuilder setVelocityFTF(Vector3 velocityFTF) {
        this.velocityFTF = velocityFTF;
        return this;
    }

    public PhysicsBodyBuilder setVelocityFTT(Vector3 velocityFTT) {
        this.velocityFTT = velocityFTT;
        return this;
    }

    public PhysicsBodyBuilder setVelocityTFF(Vector3 velocityTFF) {
        this.velocityTFF = velocityTFF;
        return this;
    }

    public PhysicsBodyBuilder setVelocityTFT(Vector3 velocityTFT) {
        this.velocityTFT = velocityTFT;
        return this;
    }

    public PhysicsBodyBuilder setVelocityTTF(Vector3 velocityTTF) {
        this.velocityTTF = velocityTTF;
        return this;
    }

    public PhysicsBodyBuilder setVelocityTTT(Vector3 velocityTTT) {
        this.velocityTTT = velocityTTT;
        return this;
    }

    public PhysicsBodyBuilder setSizeD(Vector3 sizeD) {
        this.sizeD = sizeD;
        return this;
    }

    public PhysicsBody createPhysicsBody() {
        PhysicsBody pb = new PhysicsBody(pos, rot, size, hs, isS, velocityFFF, velocityFFT, velocityFTF, velocityFTT, velocityTFF, velocityTFT, velocityTTF, velocityTTT, sizeD);
        return pb;
    }
}