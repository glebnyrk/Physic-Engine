package ru.nyrk.opengl.material;

import static ru.nyrk.opengl.material.DrawStyle.TRIANGLES;

public class SurfaceMaterial extends BasicMaterial {

    public SurfaceMaterial() {
        drawStyle = TRIANGLES;
        addRenderSetting("doubleSide", true);
        addRenderSetting("wireframe", false);
        addRenderSetting("lineWidth", 1);
    }
}
