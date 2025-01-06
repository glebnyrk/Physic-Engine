package ru.nyrk.opengl.geometry;

import ru.nyrk.opengl.Surface;
import ru.nyrk.opengl.math.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SurfaceGeometry extends Geometry {
    public SurfaceGeometry(Surface.BiFunctionDouble function,
                           double uStart, double uEnd, int uResolution,
                           double vStart, double vEnd, int vResolution) {
        Surface surface = new Surface(function);
        Vector[][] positions = surface.getPoints(
                uStart, uEnd, uResolution,
                vStart, vEnd, vResolution);
        List<Vector> quadColors = Arrays.asList(
                new Vector(1, 0, 0), new Vector(0, 1, 0),
                new Vector(0, 0, 1), new Vector(0, 1, 1),
                new Vector(1, 0, 1), new Vector(1, 1, 0));
        List<Vector> positionList = new ArrayList<>();
        List<Vector> colorList = new ArrayList<>();
        for (int uIndex = 0; uIndex < uResolution; uIndex++) {
            for (int vIndex = 0; vIndex < vResolution; vIndex++) {
                // position coordinates
                Vector pA = positions[uIndex + 0][vIndex + 0];
                Vector pB = positions[uIndex + 1][vIndex + 0];
                Vector pD = positions[uIndex + 0][vIndex + 1];
                Vector pC = positions[uIndex + 1][vIndex + 1];
                positionList.addAll(Arrays.asList(pA, pB, pC, pA, pC, pD));
                colorList.addAll(quadColors);
            }
        }

        float[] positionData = Vector.flattenList(positionList);
        float[] colorData = Vector.flattenList(colorList);
        addAttribute("vec3", "vertexPosition", positionData);
        addAttribute("vec3", "vertexColor", colorData);
        vertexCount = uResolution * vResolution * 6;
    }
}
