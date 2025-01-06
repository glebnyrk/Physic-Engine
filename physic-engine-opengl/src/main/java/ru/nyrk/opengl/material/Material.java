package ru.nyrk.opengl.material;

import ru.nyrk.opengl.OpenGLUtil;
import ru.nyrk.opengl.Uniform;

import java.util.HashMap;
import java.util.Map;

import static ru.nyrk.opengl.material.DrawStyle.TRIANGLES;

/**
 * содержит глобальные переменные и
 */
public class Material {
    public int programRef;
    public DrawStyle drawStyle;

    // Store Uniform objects,
    // indexed by name of associated variable in shader.
    public Map<String, Uniform> uniforms;
    // Store OpenGL render settings,
    // indexed by variable name.
    // Additional settings added by extending classes.
    public HashMap<String, RenderSetting> renderSettings;

    public Material(String vertexShaderFileName,
                    String fragmentShaderFileName) {
        programRef = OpenGLUtil.initFromFiles(vertexShaderFileName, fragmentShaderFileName);
        drawStyle = TRIANGLES;
        uniforms = new HashMap<>();
        // Each shader typically contains these uniforms;
        // values set during render process from Mesh/Camera.
        // Additional uniforms added by extending classes.
        addUniform("mat4", "modelMatrix", null);
        addUniform("mat4", "viewMatrix", null);
        addUniform("mat4", "projectionMatrix", null);
        renderSettings = new HashMap<>();
    }

    public void addUniform(String dataType,
                           String variableName, Object data) {
        uniforms.put(variableName, new Uniform<>(dataType, data));
    }

    // initialize all uniform variable references
    public void locateUniforms() {
        for (String variableName : uniforms.keySet()) {
            Uniform<?> uniform = uniforms.get(variableName);

            uniform.locateVariable(programRef, variableName);
        }
    }

    public void addRenderSetting(String settingName,
                                 Object data) {
        renderSettings.put(settingName,
                new RenderSetting(settingName, data));
    }
}