package ru.nyrk.opengl.material;

import static org.lwjgl.opengl.GL11.*;

public enum DrawStyle {


    POINTS(GL_POINTS),
    LINES(GL_LINES),
    LINE_LOOP(GL_LINE_LOOP),
    LINE_STRIP(GL_LINE_STRIP),
    TRIANGLES(GL_TRIANGLES),
    TRIANGLE_STRIP(GL_TRIANGLE_STRIP),
    TRIANGLE_FAN(GL_TRIANGLE_FAN),
    QUADS(GL_QUADS),
    QUAD_STRIP(GL_QUAD_STRIP),
    POLYGON(GL_POLYGON);

    private final int value;

    private DrawStyle(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
