package ru.nyrk.opengl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import static org.lwjgl.opengl.GL20.*;


public class OpenGLUtil {

    public static int initFromFiles(String vertexShaderFileName, String fragmentShaderFileName) {
        return initProgram(
                readResource(vertexShaderFileName),
                readResource(fragmentShaderFileName));
    }

    public static String readResource(String fileName) {
        try {
            URL resource = Thread.currentThread().getContextClassLoader().getResource(fileName);
            if (resource == null) {
                throw new FileNotFoundException(fileName);
            }
            try (var is = resource.openStream()) {
                return new String(is.readAllBytes());
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    // массив, используемый для хранения отладочных
    static int[] status = new int[1];

    public static int initShader(String shaderCode,
                                 int shaderType) {
        // specify specific OpenGL version
        shaderCode = "#version 330 \n" + shaderCode;
        // create empty shader object and return reference value
        int shaderRef = glCreateShader(shaderType);
        // stores the source code in the shader
        glShaderSource(shaderRef, shaderCode);
        // compiles source code stored in the shader object
        glCompileShader(shaderRef);
        // query whether shader compile was successful
        glGetShaderiv(shaderRef, GL_COMPILE_STATUS, status);
        if (status[0] == GL_FALSE) {
            // retrieve error message
            String errorMessage = glGetShaderInfoLog(shaderRef);
            // free memory used to store shader program
            glDeleteShader(shaderRef);
            // halt program and print error message
            throw new RuntimeException(errorMessage);
        }
        // compilation was successful;
        // return shader reference value
        return shaderRef;
    }

    public static int initProgram(
            String vertexShaderCode, String fragmentShaderCode) {
        int vertexShaderRef = initShader(vertexShaderCode, GL_VERTEX_SHADER);
        int fragmentShaderRef = initShader(fragmentShaderCode, GL_FRAGMENT_SHADER);
        // create empty program object and store reference to it
        int programRef = glCreateProgram();
        // attach previously compiled shader programs
        glAttachShader(programRef, vertexShaderRef);
        glAttachShader(programRef, fragmentShaderRef);
        // link vertex shader to fragment shader
        glLinkProgram(programRef);
        // query whether program link was successful
        glGetProgramiv(programRef, GL_LINK_STATUS, status);
        if (status[0] == GL_FALSE) {
            // retrieve error message
            String errorMessage =
                    glGetProgramInfoLog(programRef);
            // free memory used to store program
            glDeleteProgram(programRef);
            // halt application and print error message
            throw new RuntimeException(errorMessage);
        }
        // linking was successful;
        // return program reference value
        return programRef;
    }
}
