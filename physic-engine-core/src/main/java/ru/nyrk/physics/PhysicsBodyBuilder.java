package ru.nyrk.physics;

import ru.nyrk.hitboxes.HitBox;
import ru.nyrk.hitboxes.MeshHitBox;
import ru.nyrk.maths.Quaternion;
import ru.nyrk.maths.Vector3;

import java.util.List;

public class PhysicsBodyBuilder {
    private Vector3 pos = Vector3.ZERO;
    private Quaternion rot = Quaternion.ZERO;
    private Vector3 size = Vector3.ONE;
    private List<HitBox> hs = null;
    private boolean isS = false;
    private Vector3 impulse = Vector3.ZERO;
    private Vector3 sizeD = Vector3.ONE;

    public PhysicsBodyBuilder setPos(Vector3 posN) {
        this.pos = posN;
        return this;
    }

    public PhysicsBodyBuilder setImpulse(Vector3 v) {
        impulse = v;
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

    public PhysicsBodyBuilder setHitBoxes(List<HitBox> hsN) {
        this.hs = hsN;
        return this;
    }

    public PhysicsBodyBuilder setStatic() {
        this.isS = true;
        return this;
    }

    public PhysicsBodyBuilder setSizeD(Vector3 sizeD) {
        this.sizeD = sizeD;
        return this;
    }

    public PhysicsBody createPhysicsBody() {
        PhysicsBody pb = new PhysicsBody(pos, rot, size, hs, isS,new ImpulseMap(impulse) , sizeD);
        return pb;
    }
}