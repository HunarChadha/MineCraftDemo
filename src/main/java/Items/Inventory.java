package Items;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import Core.Camera;
import Core.TransFormation;
import GamePhysics.GeneralPhysics;

public class Inventory {
    int GAME_HEIGHT;
    int GAME_WIDTH;
    Vector2f MousePos;
    public Inventory(int GAME_HEIGHT, int GAME_WIDTH){
        this.GAME_HEIGHT = GAME_HEIGHT;
        this.GAME_WIDTH = GAME_WIDTH;
        MousePos = new Vector2f();
    }
    public void calcMousePos(TransFormation transFormation, Camera camera){
        float MouseX = camera.getMousePos().x;
        float MouseY = camera.getMousePos().y;

        float x = (2 * MouseX) / GAME_WIDTH - 1.0f;
        float y = 1.0f - (2 * MouseY) / GAME_HEIGHT;
        MousePos.set(x, y);
    }
}