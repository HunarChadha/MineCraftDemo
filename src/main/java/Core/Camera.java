package Core;
import static org.lwjgl.glfw.GLFW.*;

import GamePhysics.GeneralPhysics;
import Terrain.GenerateTerrain;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWCursorPosCallback;

public class Camera {
    Vector3f Position;
    Vector3f rotation;
    public Vector3f cameraInc;
    Vector2d MousePos;
    Vector2d InitialMousePos;
    Vector3f CameraPos;
    Vector3f InitialPlayerPos;
    Vector3f EntityPos;
    Vector3f PLayerPos;
    boolean STARTGAME = true;

    public static boolean WindowOpen = true;
    public Camera(){
        Position = new Vector3f(0, 0, 0);
        rotation = new Vector3f(0, 0, 0);
        cameraInc = new Vector3f(0, 0, 0);
        MousePos = new Vector2d(0, 0);
        InitialMousePos = new Vector2d(0, 0);
        InitialPlayerPos = new Vector3f(0, 0, 0);
        CameraPos = new Vector3f(0, 0, 0);
        EntityPos = new Vector3f(-0.2f, 0, -2);
        PLayerPos = new Vector3f(0, 0, 0);
    }
    public void setPosition(float OffsetX, float OffsetY, float OffsetZ, Vector3f Position, float SPEED){
        if(OffsetZ == 1){
            Position.x -= Math.sin(Math.toRadians(rotation.y)) * SPEED;
            Position.z += Math.cos(Math.toRadians(rotation.y)) * SPEED;

        }
        if(OffsetX == 1){
            Position.x += Math.sin(Math.toRadians(rotation.y - 90)) * SPEED;
            Position.z -= Math.cos(Math.toRadians(rotation.y - 90)) * SPEED;

        }
        if(OffsetX == - 1){
            Position.x += Math.sin(Math.toRadians(rotation.y + 90)) * SPEED;
            Position.z -= Math.cos(Math.toRadians(rotation.y + 90)) * SPEED;

        }
        if(OffsetZ == -1){
            Position.x += Math.sin(Math.toRadians(rotation.y)) * SPEED;
            Position.z -= Math.cos(Math.toRadians(rotation.y)) * SPEED;

        }
        if(OffsetY == -1){
            Position.y += -SPEED;
        }
        if(OffsetY == 1){
            Position.y += SPEED;
        }
    }
    public boolean isKeyPressed(int keyCode, long window){
        return glfwGetKey(window, keyCode) != GLFW_RELEASE;
    }
    public boolean isKeyReleased(int keyCode, long window){
        return glfwGetKey(window, keyCode) == GLFW_RELEASE;
    }
    public boolean isButtonPresed(int ButtonCode, long window){
        return glfwGetMouseButton(window, ButtonCode) != GLFW_RELEASE;
    }

    public void getGetMousePos(long window){
        GLFWCursorPosCallback cursorPosCallback;

        glfwSetCursorPosCallback(window, cursorPosCallback = GLFWCursorPosCallback.create((Window, xPos, yPos)->{
            MousePos.x =  xPos;
            MousePos.y = yPos;
        }));
    }
    public void setRotation(long window){
        if(InitialMousePos.x != MousePos.x){
            if(MousePos.x > InitialMousePos.x){
                rotation.y += 2;
                InitialMousePos.x = MousePos.x;
            }
            if(MousePos.x < InitialMousePos.x){
                rotation.y -= 2;
                InitialMousePos.x = MousePos.x;
            }
        }
        if(InitialMousePos.y != MousePos.y){
            if(MousePos.y > InitialMousePos.y){
                rotation.x += 1;
                InitialMousePos.y = MousePos.y;
            }
            if(MousePos.y < InitialMousePos.y){
                rotation.x -= 1;
                InitialMousePos.y = MousePos.y;
            }
        }
        if(rotation.y > 360){
            rotation.y = 0;
        }
        if(rotation.x > 180){
            rotation.x = 180;
        }
        if(rotation.x < -180){
            rotation.x = -180;
        }
    }
    public void MovePosition(long window){
        cameraInc.set(0, 0, 0);
        if(isKeyPressed(GLFW_KEY_W, window)){
            cameraInc.z = 1;
        }
        if(isKeyPressed(GLFW_KEY_S, window)){
            cameraInc.z = -1;
        }
        if(isKeyPressed(GLFW_KEY_A, window)){
            cameraInc.x = -1;
        }
        if(isKeyPressed(GLFW_KEY_D, window)){
            cameraInc.x =   1;
        }
        if(isKeyPressed(GLFW_KEY_ENTER, window)){
            WindowOpen = false;
        }
        if(isKeyPressed(GLFW_KEY_E, window)){
            cameraInc.y = -1;
        }
        if(isKeyPressed(GLFW_KEY_Q, window)){
            //STARTGAME = false;
            cameraInc.y = 1;
        }
    }
    public void setGameComp(Vector3f cameraInc, long window, GenerateTerrain terrain){
        setPosition(cameraInc.x, cameraInc.y, cameraInc.z, Position, 0.5f);
        PLayerPos.set(-getPosition().x, -getPosition().y, -getPosition().z);
        //Jump(window);
        //checkCollision(physics);
        //setGravity(physics);
        //for(int i = 0; i<terrain.Pos2DArray.length; i++){
            //setPosition(cameraInc.x, cameraInc.y, cameraInc.z, terrain.Pos2DArray[i][0], 0.05f);
        //}
        setRotation(window);
    }

    public void Jump(long window){
        if(getPLayerPos().y <= 0){
            if(isKeyPressed(GLFW_KEY_SPACE, window)){
                getPosition().y -= 6f;
            }
        }
    }
    public void setGravity(GeneralPhysics physics){
        if(STARTGAME){
            if(getPLayerPos().y > 0){
                getPosition().y += 0.5f;
            }
        }
        PLayerPos.set(-getPosition().x, -getPosition().y, -getPosition().z);
    }
    public Vector3f getPosition(){
        return Position;
    }
    public Vector3f getRotation(){return rotation;}
    public Vector3f getCameraPos(){return CameraPos;}
    public Vector3f getPLayerPos(){return PLayerPos;}
    public float CalculateDistance(Vector3f MidPoint, Vector3f CameraPos){
        float Distance;
        float xDistance = (float) Math.pow((MidPoint.x - CameraPos.x), 2);
        float zDistance = (float) Math.pow((MidPoint.z - CameraPos.z), 2);
        Distance = (float) Math.sqrt(xDistance  + zDistance);
        return Distance;
    }
    public Vector2f getMousePos(){
        float x = (float) MousePos.x;
        float y = (float) MousePos.y;
        return new Vector2f(x, y);
    }
}