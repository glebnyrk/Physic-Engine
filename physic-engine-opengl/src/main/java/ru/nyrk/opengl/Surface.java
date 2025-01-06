package ru.nyrk.opengl;

import ru.nyrk.opengl.math.Vector;

public class Surface {

    public interface BiFunctionDouble {
        Vector apply(double u, double v);
    }

    public BiFunctionDouble function;

    public Surface(BiFunctionDouble function) {
        this.function = function;
    }

    public Vector[][] getPoints(
            double uStart, double uEnd, int uResolution,
            double vStart, double vEnd, int vResolution) {
        Vector[][] points = new Vector[uResolution + 1][vResolution + 1];
        double deltaU = (uEnd - uStart) / uResolution;
        double deltaV = (vEnd - vStart) / vResolution;
        for (int uIndex = 0; uIndex < uResolution + 1; uIndex++) {
            for (int vIndex = 0; vIndex < vResolution + 1; vIndex++) {
                double u = uStart + uIndex * deltaU;
                double v = vStart + vIndex * deltaV;
                points[uIndex][vIndex] = this.function.apply(u, v);
            }
        }
        return points;
    }
}
