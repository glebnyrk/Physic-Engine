package ru.nyrk.physics;

import ru.nyrk.maths.Quaternion;
import ru.nyrk.maths.Vector3;

import java.util.StringJoiner;

/**
 * Карта импульсов для удобной структуризации импульсов
 */
public final class ImpulseMap {
    private final Vector3[] impulses = new Vector3[]{Vector3.ZERO, Vector3.ZERO, Vector3.ZERO, Vector3.ZERO, Vector3.ZERO, Vector3.ZERO, Vector3.ZERO, Vector3.ZERO};

    ImpulseMap(Vector3 fffCorner,
               Vector3 fftCorner,
               Vector3 ftfCorner,
               Vector3 fttCorner,
               Vector3 tffCorner,
               Vector3 tftCorner,
               Vector3 ttfCorner,
               Vector3 tttCorner) {
        impulses[0] = fffCorner;
        impulses[1] = fftCorner;
        impulses[2] = ftfCorner;
        impulses[3] = fttCorner;
        impulses[4] = tffCorner;
        impulses[5] = tftCorner;
        impulses[6] = ttfCorner;
        impulses[7] = tttCorner;
    }

    public ImpulseMap(Vector3 startImpulse) {
        add(startImpulse);
    }

    private Vector3 getCornerImpulse(ImpulseCorner corner) {
        return impulses[corner.ordinal()];
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

    //TODO
    public Vector3 getImpulse(Vector3 where) {
        return getImpulse();
    }
    //TODO
    public void addImpulse(Vector3 where, Vector3 impulse) {
        add(impulse);
    }
    //TODO
    public Quaternion rotationForce(){
        return Quaternion.ZERO;
    }
    /**
     * Добавление карты импульсов к существующей
     */
    public void add(ImpulseMap map) {
        for (int i = 0; i < 8; i++) {
            impulses[i] = impulses[i].add(map.impulses[i]);
        }
    }

    /**
     * Распределение импульса по всей карте и сложение
     */
    public void add(Vector3 vector) {
        vector = vector.mul(1f / 8f);
        for (int i = 0; i < 8; i++) {
            impulses[i] = impulses[i].add(vector);
        }
    }

    /**
     * Умножение всех импульсов на b
     */
    public void mul(float b) {
        for (int i = 0; i < 8; i++) {
            impulses[i] = impulses[i].mul(b);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        StringJoiner sj = new StringJoiner(", \n", "ImpulseMap:{ ", "}");
        for (int i = 0; i < 8; i++) {
            sj.add(impulses[i].toString());
        }
        return sj.toString();
    }
}
