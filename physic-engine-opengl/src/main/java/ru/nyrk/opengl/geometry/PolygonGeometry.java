package ru.nyrk.opengl.geometry;

import ru.nyrk.opengl.math.Vector;

import java.util.ArrayList;
import java.util.List;

public class PolygonGeometry extends Geometry {
    public PolygonGeometry() {
        this(1, 1);
    }

    public PolygonGeometry(int sides, double radius) {
        float A = (float) (2 * Math.PI / sides);
        List<Vector> positionList = new ArrayList<Vector>();
        List<Vector> colorList = new ArrayList<Vector>();
        Vector Z = new Vector(0, 0, 0);
        Vector C1 = new Vector(1, 1, 1);
        Vector C2 = new Vector(1, 0, 0);
        Vector C3 = new Vector(0, 0, 1);
        for (int n = 0; n < sides; n++) {
            positionList.add(Z);
            positionList.add(new Vector(
                    radius * Math.cos(n * A),
                    radius * Math.sin(n * A), 0));
            positionList.add(new Vector(
                    radius * Math.cos((n + 1) * A),
                    radius * Math.sin((n + 1) * A), 0));
            colorList.add(C1);
            colorList.add(C2);
            colorList.add(C3);
        }
        float[] positionData = Vector.flattenList(positionList);
        float[] colorData = Vector.flattenList(colorList);
        addAttribute("vec3", "vertexPosition", positionData);
        addAttribute("vec3", "vertexColor", colorData);
        vertexCount = sides * 3;
    }
}
