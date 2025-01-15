package ru.nyrk.opengl;

import ru.nyrk.opengl.math.Matrix;
import ru.nyrk.opengl.math.Vector;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main3d extends Base {
    private Uniform<Matrix> modelMatrix;
    private Uniform<Matrix> projectionMatrix;
    private float moveSpeed;
    private float turnSpeed;

    public static void main(String[] args) {
        new Main3d().run();
    }

    public float speed = 0.5f;

    public int programRef, vaoRef;

    public Uniform<Vector> translation, baseColor;


    @Override
    public void initialize() {
        glEnable(GL_DEPTH_TEST);

        System.out.println("Поставщик: " + glGetString(GL_VENDOR));
        System.out.println("Рендер: " + glGetString(GL_RENDERER));
        System.out.println("Поддерживаемая версия OpenGL: " + glGetString(GL_VERSION));
        System.out.println("Initializing program...");

        // настройки рендеринга (необязательно)
        glLineWidth(4);
        glPointSize(10);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glEnable(GL_DEPTH_TEST);

        // настройка объекта массива вершин int

        vaoRef = glGenVertexArrays();
        glBindVertexArray(vaoRef);
        float[] positionData = {
                0.0f, 0.2f, 0.0f,
                0.2f, -0.2f, 0.0f,
                -0.2f, -0.2f, 0.0f};
        Attribute positionAttribute = new Attribute("vec3", positionData);
        positionAttribute.associateVariable(programRef, "position");

        Matrix mMatrix = Matrix.makeTranslation(0, 0, -1);
        modelMatrix = new Uniform<Matrix>("mat4", mMatrix);
        modelMatrix.locateVariable(programRef, "modelMatrix");

        Matrix pMatrix = Matrix.makePerspective();
        projectionMatrix = new Uniform<Matrix>("mat4", pMatrix);
        projectionMatrix.locateVariable(programRef, "projectionMatrix");
        // movement speed, units per second
        moveSpeed = 0.5f;
// rotation speed, radians per second
        turnSpeed = (float) Math.toRadians(90);
    }

    @Override
    public void update() {
        // update data
        float moveAmount = moveSpeed * deltaTime;
        float turnAmount = turnSpeed * deltaTime;
        if (input.isKeyPressed(GLFW_KEY_W))
            modelMatrix.data.leftMultiply(
                    Matrix.makeTranslation(0, moveAmount, 0));
        if (input.isKeyPressed(GLFW_KEY_S))
            modelMatrix.data.leftMultiply(
                    Matrix.makeTranslation(0, -moveAmount, 0));
        if (input.isKeyPressed(GLFW_KEY_A))
            modelMatrix.data.leftMultiply(
                    Matrix.makeTranslation(-moveAmount, 0, 0));
        if (input.isKeyPressed(GLFW_KEY_D))
            modelMatrix.data.leftMultiply(
                    Matrix.makeTranslation(moveAmount, 0, 0));

        if (input.isKeyPressed(GLFW_KEY_Z))
            modelMatrix.data.leftMultiply(
                    Matrix.makeTranslation(0, 0, moveAmount));
        if (input.isKeyPressed(GLFW_KEY_X))
            modelMatrix.data.leftMultiply(
                    Matrix.makeTranslation(0, 0, -moveAmount));

        // global rotation
        if (input.isKeyPressed(GLFW_KEY_Q))
            modelMatrix.data.leftMultiply(
                    Matrix.makeRotationZ(turnAmount));
        if (input.isKeyPressed(GLFW_KEY_E))
            modelMatrix.data.leftMultiply(
                    Matrix.makeRotationZ(-turnAmount));

        // local translation
        if (input.isKeyPressed(GLFW_KEY_I))
            modelMatrix.data.rightMultiply(
                    Matrix.makeTranslation(0, moveAmount, 0));
        if (input.isKeyPressed(GLFW_KEY_K))
            modelMatrix.data.rightMultiply(
                    Matrix.makeTranslation(0, -moveAmount, 0));
        if (input.isKeyPressed(GLFW_KEY_J))
            modelMatrix.data.rightMultiply(
                    Matrix.makeTranslation(-moveAmount, 0, 0));
        if (input.isKeyPressed(GLFW_KEY_L))
            modelMatrix.data.rightMultiply(
                    Matrix.makeTranslation(moveAmount, 0, 0));

        // local rotation
        if (input.isKeyPressed(GLFW_KEY_U))
            modelMatrix.data.rightMultiply(
                    Matrix.makeRotationZ(turnAmount));
        if (input.isKeyPressed(GLFW_KEY_O))
            modelMatrix.data.rightMultiply(
                    Matrix.makeRotationZ(-turnAmount));
        // render scene
// reset color buffer with specified color
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glUseProgram(programRef);
        glBindVertexArray(vaoRef);
        modelMatrix.uploadData();
        projectionMatrix.uploadData();
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }
}