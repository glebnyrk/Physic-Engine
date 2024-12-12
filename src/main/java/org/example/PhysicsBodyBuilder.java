package org.example;

public class PhysicsBodyBuilder {
    private Vector3 pos = Vector3.ZERO;
    private Quaternion rot = Quaternion.ZERO;
    private Vector3 size = Vector3.ONE;
    private Hitbox[] hs = null;
    private boolean isS = false;
    private Vector3 impulseFFF = Vector3.ZERO;
    private Vector3 impulseFFT = Vector3.ZERO;
    private Vector3 impulseFTF = Vector3.ZERO;
    private Vector3 impulseFTT = Vector3.ZERO;
    private Vector3 impulseTFF = Vector3.ZERO;
    private Vector3 impulseTFT = Vector3.ZERO;
    private Vector3 impulseTTF = Vector3.ZERO;
    private Vector3 impulseTTT = Vector3.ZERO;
    private Vector3 sizeD  = Vector3.ONE;

    public PhysicsBodyBuilder setPos(Vector3 posN) {
        this.pos = posN;
        return this;
    }
    public PhysicsBodyBuilder setImpulse(Vector3 v) {
        v = v.mul(1f/8f);
        this.impulseFFF = v;
        this.impulseFFT = v;
        this.impulseFTF = v;
        this.impulseFTT = v;
        this.impulseTFF = v;
        this.impulseTFT = v;
        this.impulseTTF = v;
        this.impulseTTT = v;
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

    public PhysicsBodyBuilder setImpulseFFF(Vector3 impulseFFF) {
        this.impulseFFF = impulseFFF;
        return this;
    }

    public PhysicsBodyBuilder setImpulseFFT(Vector3 impulseFFT) {
        this.impulseFFT = impulseFFT;
        return this;
    }

    public PhysicsBodyBuilder setImpulseFTF(Vector3 impulseFTF) {
        this.impulseFTF = impulseFTF;
        return this;
    }

    public PhysicsBodyBuilder setImpulseFTT(Vector3 impulseFTT) {
        this.impulseFTT = impulseFTT;
        return this;
    }

    public PhysicsBodyBuilder setImpulseTFF(Vector3 impulseTFF) {
        this.impulseTFF = impulseTFF;
        return this;
    }

    public PhysicsBodyBuilder setImpulseTFT(Vector3 impulseTFT) {
        this.impulseTFT = impulseTFT;
        return this;
    }

    public PhysicsBodyBuilder setImpulseTTF(Vector3 impulseTTF) {
        this.impulseTTF = impulseTTF;
        return this;
    }

    public PhysicsBodyBuilder setImpulseTTT(Vector3 impulseTTT) {
        this.impulseTTT = impulseTTT;
        return this;
    }

    public PhysicsBodyBuilder setSizeD(Vector3 sizeD) {
        this.sizeD = sizeD;
        return this;
    }

    public PhysicsBody createPhysicsBody() {
        PhysicsBody pb = new PhysicsBody(pos, rot, size, hs, isS, impulseFFF, impulseFFT, impulseFTF, impulseFTT, impulseTFF, impulseTFT, impulseTTF, impulseTTT, sizeD);
        return pb;
    }
}