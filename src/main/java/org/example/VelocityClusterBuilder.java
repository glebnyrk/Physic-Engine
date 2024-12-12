package org.example;

public class VelocityClusterBuilder {
    private Vector3 fffCorner = Vector3.ZERO;
    private Vector3 fftCorner = Vector3.ZERO;
    private Vector3 ftfCorner = Vector3.ZERO;
    private Vector3 fttCorner = Vector3.ZERO;
    private Vector3 tffCorner = Vector3.ZERO;
    private Vector3 tftCorner = Vector3.ZERO;
    private Vector3 ttfCorner = Vector3.ZERO;
    private Vector3 tttCorner = Vector3.ZERO;

    public VelocityClusterBuilder setFffCorner(Vector3 fffCorner) {
        this.fffCorner = fffCorner;
        return this;
    }

    public VelocityClusterBuilder setFftCorner(Vector3 fftCorner) {
        this.fftCorner = fftCorner;
        return this;
    }

    public VelocityClusterBuilder setFtfCorner(Vector3 ftfCorner) {
        this.ftfCorner = ftfCorner;
        return this;
    }

    public VelocityClusterBuilder setFttCorner(Vector3 fttCorner) {
        this.fttCorner = fttCorner;
        return this;
    }

    public VelocityClusterBuilder setTffCorner(Vector3 tffCorner) {
        this.tffCorner = tffCorner;
        return this;
    }

    public VelocityClusterBuilder setTftCorner(Vector3 tftCorner) {
        this.tftCorner = tftCorner;
        return this;
    }

    public VelocityClusterBuilder setTtfCorner(Vector3 ttfCorner) {
        this.ttfCorner = ttfCorner;
        return this;
    }

    public VelocityClusterBuilder setTttCorner(Vector3 tttCorner) {
        this.tttCorner = tttCorner;
        return this;
    }

    public VelocityCluster createVelocityCluster() {
        return new VelocityCluster(fffCorner, fftCorner, ftfCorner, fttCorner, tffCorner, tftCorner, ttfCorner, tttCorner);
    }
}