package ru.nyrk.opengl.geometry;

import ru.nyrk.hitboxes.BoxHitBox;
import ru.nyrk.maths.Vector3;
import ru.nyrk.opengl.math.Vector;

import java.util.Arrays;
import java.util.List;

public class BoxGeometry extends Geometry {
    public BoxGeometry() {
        this(1, 1, 1);
    }

    public BoxGeometry(BoxHitBox boxHitBox) {
        List<Vector3> globalPoints = boxHitBox.getGlobalPoints();
        // corners of a cube
        if (globalPoints.size() != 8) {
            throw new IllegalArgumentException("globalPoints.size() != 8");
        }
        Vector P0 = Vector.of(globalPoints.get(0));
        Vector P1 = Vector.of(globalPoints.get(1));
        Vector P2 = Vector.of(globalPoints.get(2));
        Vector P3 = Vector.of(globalPoints.get(3));
        Vector P4 = Vector.of(globalPoints.get(4));
        Vector P5 = Vector.of(globalPoints.get(5));
        Vector P6 = Vector.of(globalPoints.get(6));
        Vector P7 = Vector.of(globalPoints.get(7));
        init(P0, P1, P2, P3, P4, P5, P6, P7);

    }

    public BoxGeometry(double width, double height, double depth) {
        // corners of a cube
        Vector P0 = new Vector(-width / 2, -height / 2, -depth / 2);
        Vector P1 = new Vector(width / 2, -height / 2, -depth / 2);
        Vector P2 = new Vector(-width / 2, height / 2, -depth / 2);
        Vector P3 = new Vector(width / 2, height / 2, -depth / 2);
        Vector P4 = new Vector(-width / 2, -height / 2, depth / 2);
        Vector P5 = new Vector(width / 2, -height / 2, depth / 2);
        Vector P6 = new Vector(-width / 2, height / 2, depth / 2);
        Vector P7 = new Vector(width / 2, height / 2, depth / 2);
        init(P0, P1, P2, P3, P4, P5, P6, P7);
    }

    private void init(Vector P0, Vector P1, Vector P2, Vector P3, Vector P4, Vector P5, Vector P6, Vector P7) {

        // colors for faces in order: x+, x-, y+, y-, z+, z-
        Vector C1 = new Vector(1.0f, 0.5f, 0.5f);
        Vector C2 = new Vector(0.5f, 0.0f, 0.0f);
        Vector C3 = new Vector(0.5f, 1.0f, 0.5f);
        Vector C4 = new Vector(0.0f, 0.5f, 0.0f);
        Vector C5 = new Vector(0.5f, 0.5f, 1.0f);
        Vector C6 = new Vector(0.0f, 0.0f, 0.5f);

        List<Vector> positionList = Arrays.asList(
                P5, P1, P3, P5, P3, P7, P0, P4, P6, P0, P6, P2,
                P6, P7, P3, P6, P3, P2, P0, P1, P5, P0, P5, P4,
                P4, P5, P7, P4, P7, P6, P1, P0, P2, P1, P2, P3);
        float[] positionData = Vector.flattenList(positionList);
        List<Vector> colorList = Arrays.asList(
                C1, C1, C1, C1, C1, C1, C2, C2, C2, C2, C2, C2,
                C3, C3, C3, C3, C3, C3, C4, C4, C4, C4, C4, C4,
                C5, C5, C5, C5, C5, C5, C6, C6, C6, C6, C6, C6);
        float[] colorData = Vector.flattenList(colorList);

        addAttribute("vec3", "vertexPosition", positionData);
        addAttribute("vec3", "vertexColor", colorData);
        vertexCount = 36;
    }

}
