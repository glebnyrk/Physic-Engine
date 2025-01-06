package ru.nyrk.opengl.geometry;

import ru.nyrk.opengl.math.Vector;

public class PlaneGeometry extends SurfaceGeometry {
    public PlaneGeometry(double width, double height,
                         int widthSegments, int heightSegments) {

        super((v, v2) -> new Vector(v, v2, 0), -width / 2, width / 2, widthSegments,
                -height / 2, height / 2, heightSegments);
    }

    public PlaneGeometry() {
        this(1, 1, 8, 8);
    }

}
