package ru.nyrk.opengl.material;

import ru.nyrk.opengl.math.Vector;

public class BasicMaterial extends Material {
    public BasicMaterial() {
        super("BasicMaterial.vert", "BasicMaterial.frag");
        addUniform("vec3", "baseColor", new Vector(1, 1, 1));
        addUniform("bool", "useVertexColors", 0);
        locateUniforms();
    }
}
