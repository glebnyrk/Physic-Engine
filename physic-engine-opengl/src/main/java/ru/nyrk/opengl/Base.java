package ru.nyrk.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.system.Callback;

import java.util.Optional;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.GL_TRUE;

public abstract class Base {
    // window dimensions
    private int windowWidth;
    private int windowHeight;
    // the window handle
    private long window;
    // is the main loop currently active?
    private boolean running;
    // number of seconds application has been running
    public float time;
    // seconds since last iteration of run loop
    public float deltaTime;
    // store time data from last iteration of run loop
    private long previousTime;
    private long currentTime;
    // handle user input events
    public Input input;

    public Base() {
    }

    public void startup() {
// initialize GLFW
        boolean initSuccess = glfwInit();
        if (!initSuccess)
            throw new RuntimeException("Unable to initialize GLFW");
        // create window and associated OpenGL context,
        // which stores framebuffer and other state information
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);

        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        window = glfwCreateWindow(windowWidth, windowHeight, "Graphics Window", 0, 0);
        input = new Input(window);

        if (window == 0)
            throw new RuntimeException("Failed to create the GLFW window");
        running = true;
        // make all OpenGL function calls
        // apply to this context instance
        glfwMakeContextCurrent(window);

        // specify number of screen updates
        // to wait before swapping buffers.
        // setting to 1 synchronizes application frame rate
        // with display refresh rate;
        // prevents visual "screen tearing" artifacts
        glfwSwapInterval(1);
        // detect current context and makes
        // OpenGL bindings available for use
        GL.createCapabilities();

        // recalculate time variables
        time = 0;
        deltaTime = 1/60f;
        currentTime = System.currentTimeMillis();
        previousTime = System.currentTimeMillis();
    }

    public abstract void initialize();

    public abstract void update();

    public void run() {
        this.run(1024, 1024);
    }

    public void run(int windowWidth, int windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        startup();
        // application-specific startup code
        initialize();
        // main loop
        while (running) {
            // check for user interaction events
            glfwPollEvents();

            // recalculate time variables
            currentTime = System.currentTimeMillis();
            deltaTime = (currentTime - previousTime) / 1000f;
            time += deltaTime;
            previousTime = currentTime;

            // check if window close icon is clicked
            if (glfwWindowShouldClose(window)) {
                running = false;
            }
            input.update();
            // код обновления для конкретного приложения
            update();
            // поменять местами цветовые буферы
            // для отображения визуализированной графики на экране
            glfwSwapBuffers(window);
        }
        shutdown();
    }

    public void shutdown() {
        // stop window monitoring for user input
        glfwFreeCallbacks(window);
        // close the window
        glfwDestroyWindow(window);
        // stop GLFW
        glfwTerminate();
        // stop error callback
        Optional.ofNullable(glfwSetErrorCallback(null)).ifPresent(Callback::free);
    }
}
