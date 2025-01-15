package ru.nyrk.opengl.geometry;

import ru.nyrk.opengl.math.Vector;

import java.util.Arrays;
import java.util.List;

public class AxesGeometry extends Geometry {
    public AxesGeometry() {
        List<Vector> positions = Arrays.asList(
                new Vector(0, 0, 0), new Vector(10, 0, 0),
                new Vector(0, 0, 0), new Vector(0, 10, 0),
                new Vector(0, 0, 0), new Vector(0, 0, 10)
        );
        float[] positionData = Vector.flattenList(positions);
        List<Vector> colors = Arrays.asList(
                new Vector(0.5, 0, 0), new Vector(1, 0.5, 0.5),
                new Vector(0, 0.5, 0), new Vector(0.5, 1, 0.5),
                new Vector(0, 0, 0.5), new Vector(0.5, 0.5, 1)
        );
        float[] colorData = Vector.flattenList(colors);
        addAttribute("vec3","vertexPosition",positionData);
        addAttribute("vec3","vertexColor",colorData);
    }
}
