package ru.nyrk.opengl.material;

import static ru.nyrk.opengl.material.DrawStyle.POINTS;

public class PointMaterial extends BasicMaterial {
    public PointMaterial() {
        drawStyle = POINTS;
        addRenderSetting("pointSize", 16);
        addRenderSetting("roundedPoints", true);
    }
}
