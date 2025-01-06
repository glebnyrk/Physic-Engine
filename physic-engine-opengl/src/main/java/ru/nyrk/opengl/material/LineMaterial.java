package ru.nyrk.opengl.material;

import static ru.nyrk.opengl.material.DrawStyle.*;

public class LineMaterial extends BasicMaterial {
    public LineMaterial() {
        drawStyle = LINES;
        addRenderSetting("lineWidth", 1);
    }

    public LineMaterial(String lineStyle) {
        if (lineStyle.equals("segments"))
            drawStyle = LINES;
        else if (lineStyle.equals("connected"))
            drawStyle = LINE_STRIP;
        else if (lineStyle.equals("loop"))
            drawStyle = LINE_LOOP;
        else {
            System.out.println("Unknown line style: "
                    + lineStyle);
            drawStyle = LINES;
        }
        addRenderSetting("lineWidth", 1);
    }
}
