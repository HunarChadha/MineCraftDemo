package org.example;
import Core.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glEnable;
import Items.Recipe;
import RenderManger.GameEngine;
import RenderManger.Render;
import RenderManger.Shaders;

import static org.lwjgl.glfw.GLFW.*;
public class Window {
    private long window;
    int GAME_WIDTH;
    private long lastTime;
    private int Fps;
    int GAME_HEIGHT;
    Recipe recipe;
    GameEngine engine;
    Render render;
    Shaders shaders;
    int n = 0;
    public Window(){
        init();
    }
    public void setNewClasses() throws Exception{
        String RecipePath = "Recipe\\Recipe";
        recipe = new Recipe(RecipePath);
        render = new Render();
        shaders = new Shaders();
        engine = new GameEngine(GAME_WIDTH, GAME_HEIGHT);
    }
    public void init(){
        glfwInit();
        boolean FullScreen = true;
        Runtime runtime = Runtime.getRuntime();
        System.out.println(runtime.totalMemory());
        System.out.println(runtime.freeMemory());
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        assert vidMode != null;
        if(!FullScreen){
            GAME_WIDTH = 400;
            GAME_HEIGHT = 300;
        }
        if(FullScreen){
            GAME_WIDTH = vidMode.width();
            GAME_HEIGHT = vidMode.height();
        }
        window = glfwCreateWindow(GAME_WIDTH, GAME_HEIGHT, "3D", 0, 0);
        if(!FullScreen){
            glfwSetWindowPos(window, 500, 200);
        }
        if(FullScreen){
            glfwSetWindowPos(window, 0, 0);
        }
        glfwSwapInterval(1);
        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }
    public void update(){
        long currentTime = System.nanoTime();
        float deltaTime = (float) ((currentTime - lastTime) / 1e9);
        lastTime = currentTime;
        calcFps(deltaTime);
    }
    public void calcFps(float deltaTime){
        Fps = (int) (1.0f/deltaTime);
        System.out.println("FPS: " + Fps);
    }
    public boolean windowClose(){
        return glfwWindowShouldClose(window);
    }
    public void setMouseInput(){
        if(n == 0){
            
                glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
                n++;
        }
        if(n == 1){
            
                glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
                double MousePosX = (double) GAME_WIDTH/2;
                double MousePosY = (double) GAME_HEIGHT/2;
                //glfwSetCursorPos(window, MousePosX, MousePosY);
                n = 0;
            
        }
    }
        
    public void Loop() {
        try {
            GL.createCapabilities();
            setNewClasses();
            //glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
            glEnable(GL_DEPTH_TEST);
            glCullFace(GL_BACK);
            //glClearColor(1, 0, 1, 1);
            while (!windowClose() && Camera.WindowOpen) {
                glClear(GL_DEPTH_BUFFER_BIT|GL_COLOR_BUFFER_BIT);
                engine.RenderGameScene(render, shaders, window, recipe);
                //renderManger.RenderGui(gui, textureID);
                glfwSwapBuffers(window);
                glfwPollEvents();
            }
            glfwTerminate();
        }catch (Exception excp){
            excp.printStackTrace();
        }
        finally {
            //shaders.cleanUp();
        }
    }

}
