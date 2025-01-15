package ru.nyrk.opengl.geometry;

import ru.nyrk.opengl.Mesh;
import ru.nyrk.opengl.material.LineMaterial;

public class AxesHelper extends Mesh {
    public AxesHelper() {
        super(new AxesGeometry(), new LineMaterial());
//        this.material.uniforms.get("useVertexColors").data = 1;
        this.material.renderSettings.get("lineWidth").data
                = 8;
    }
}
