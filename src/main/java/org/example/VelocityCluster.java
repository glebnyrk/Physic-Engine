package org.example;

public final class VelocityCluster {
    private final Vector3[] velocities = new Vector3[8];
    VelocityCluster(Vector3 fffCorner,
                    Vector3 fftCorner,
                    Vector3 ftfCorner,
                    Vector3 fttCorner,
                    Vector3 tffCorner,
                    Vector3 tftCorner,
                    Vector3 ttfCorner,
                    Vector3 tttCorner){
        velocities[0] = fffCorner;
        velocities[1] = fftCorner;
        velocities[2] = ftfCorner;
        velocities[3] = fttCorner;
        velocities[4] = tffCorner;
        velocities[5] = tftCorner;
        velocities[6] = ttfCorner;
        velocities[7] = tttCorner;
    }
    public Vector3 getCornerVelocity(VelocityCorner corner){
        return velocities[corner.ordinal()];
    }
    public Vector3 getCornerVelocity(int index){
        return velocities[index];
    }
    public void setCornerVelocity(VelocityCorner corner, Vector3 vector){
        velocities[corner.ordinal()] = vector;
    }
    public Vector3 getVelocity() {
        return getCornerVelocity(VelocityCorner.FFF)
                .add(getCornerVelocity(VelocityCorner.FFT))
                .add(getCornerVelocity(VelocityCorner.FTF))
                .add(getCornerVelocity(VelocityCorner.FTT))
                .add(getCornerVelocity(VelocityCorner.TFF))
                .add(getCornerVelocity(VelocityCorner.TFF))
                .add(getCornerVelocity(VelocityCorner.TTF))
                .add(getCornerVelocity(VelocityCorner.TTT));
    }
    public void add(VelocityCluster cluster){
        for(int i = 0; i < 8; i++){
            velocities[i] = velocities[i].add(cluster.velocities[i].mul(1f/8f));
        }
    }
    public void add(Vector3 vector){
        vector = vector.mul(1f/8f);
        for(int i = 0; i < 8; i++){
            velocities[i] = velocities[i].add(vector);
        }
    }
    public void mul(float b){
        for(int i = 0; i < 8; i++){
            velocities[i] = velocities[i].mul(b);
        }
    }
}
