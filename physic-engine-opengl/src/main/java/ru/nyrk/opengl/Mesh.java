package ru.nyrk.opengl;

import ru.nyrk.opengl.geometry.Geometry;
import ru.nyrk.opengl.material.Material;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Mesh extends Object3D {
    public Geometry geometry;
    public Material material;
    // should this object be rendered?
    public boolean visible;
    // vertex array object reference
    public int vaoRef;

    public Mesh(Geometry geometry, Material material) {
        this.geometry = geometry;
        this.material = material;
        this.visible = true;
        // set up associations between
        // attributes stored in geometry
        // and shader program stored in material
        vaoRef = glGenVertexArrays();
        glBindVertexArray(vaoRef);
        for (String variableName : geometry.attributes.keySet()) {
            Attribute attribute = geometry.attributes.get(variableName);
            attribute.associateVariable(material.programRef, variableName);
        }
        // unbind this vertex array object
        glBindVertexArray(0);
    }

}
