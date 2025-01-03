package ru.nyrk.physics;

import ru.nyrk.maths.Vector3;

public class ImpulseMapBuilder {
    private Vector3 fffCorner = Vector3.ZERO;
    private Vector3 fftCorner = Vector3.ZERO;
    private Vector3 ftfCorner = Vector3.ZERO;
    private Vector3 fttCorner = Vector3.ZERO;
    private Vector3 tffCorner = Vector3.ZERO;
    private Vector3 tftCorner = Vector3.ZERO;
    private Vector3 ttfCorner = Vector3.ZERO;
    private Vector3 tttCorner = Vector3.ZERO;

    public ImpulseMapBuilder setFffCorner(Vector3 fffCorner) {
        this.fffCorner = fffCorner;
        return this;
    }

    public ImpulseMapBuilder setFftCorner(Vector3 fftCorner) {
        this.fftCorner = fftCorner;
        return this;
    }

    public ImpulseMapBuilder setFtfCorner(Vector3 ftfCorner) {
        this.ftfCorner = ftfCorner;
        return this;
    }

    public ImpulseMapBuilder setFttCorner(Vector3 fttCorner) {
        this.fttCorner = fttCorner;
        return this;
    }

    public ImpulseMapBuilder setTffCorner(Vector3 tffCorner) {
        this.tffCorner = tffCorner;
        return this;
    }

    public ImpulseMapBuilder setTftCorner(Vector3 tftCorner) {
        this.tftCorner = tftCorner;
        return this;
    }

    public ImpulseMapBuilder setTtfCorner(Vector3 ttfCorner) {
        this.ttfCorner = ttfCorner;
        return this;
    }

    public ImpulseMapBuilder setTttCorner(Vector3 tttCorner) {
        this.tttCorner = tttCorner;
        return this;
    }

    public ImpulseMapBuilder setImpulse(Vector3 impulse) {
        impulse = impulse.mul(1f / 8f);
        this.fffCorner = impulse;
        this.fftCorner = impulse;
        this.ftfCorner = impulse;
        this.fttCorner = impulse;
        this.tffCorner = impulse;
        this.tftCorner = impulse;
        this.ttfCorner = impulse;
        this.tttCorner = impulse;
        return this;
    }

    public ImpulseMap createImpulseCluster() {
        return new ImpulseMap(fffCorner, fftCorner, ftfCorner, fttCorner, tffCorner, tftCorner, ttfCorner, tttCorner);
    }
}