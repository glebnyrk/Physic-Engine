package org.example;

public final class ImpulseMap {
    private final Vector3[] impulses = new Vector3[8];
    ImpulseMap(Vector3 fffCorner,
               Vector3 fftCorner,
               Vector3 ftfCorner,
               Vector3 fttCorner,
               Vector3 tffCorner,
               Vector3 tftCorner,
               Vector3 ttfCorner,
               Vector3 tttCorner){
        impulses[0] = fffCorner;
        impulses[1] = fftCorner;
        impulses[2] = ftfCorner;
        impulses[3] = fttCorner;
        impulses[4] = tffCorner;
        impulses[5] = tftCorner;
        impulses[6] = ttfCorner;
        impulses[7] = tttCorner;
    }
    public Vector3 getCornerImpulse(ImpulseCorner corner){
        return impulses[corner.ordinal()];
    }
    public Vector3 getCornerImpulse(int index){
        return impulses[index];
    }
    public void setCornerImpulse(ImpulseCorner corner, Vector3 vector){
        impulses[corner.ordinal()] = vector;
    }
    public Vector3 getImpulse() {
        return getCornerImpulse(ImpulseCorner.FFF)
                .add(getCornerImpulse(ImpulseCorner.FFT))
                .add(getCornerImpulse(ImpulseCorner.FTF))
                .add(getCornerImpulse(ImpulseCorner.FTT))
                .add(getCornerImpulse(ImpulseCorner.TFF))
                .add(getCornerImpulse(ImpulseCorner.TFF))
                .add(getCornerImpulse(ImpulseCorner.TTF))
                .add(getCornerImpulse(ImpulseCorner.TTT));
    }
    public void add(ImpulseMap cluster){
        for(int i = 0; i < 8; i++){
            impulses[i] = impulses[i].add(cluster.impulses[i]);
        }
    }
    public void add(Vector3 vector){
        vector = vector.mul(1f/8f);
        for(int i = 0; i < 8; i++){
            impulses[i] = impulses[i].add(vector);
        }
    }
    public void mul(float b){
        for(int i = 0; i < 8; i++){
            impulses[i] = impulses[i].mul(b);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ImpulseMap:{ ");
        for (int i = 0; i < 8; i++) {
            sb.append(impulses[i]);
            sb.append(", \n");
        }
        sb.append("}");
        return sb.toString();
    }
}
