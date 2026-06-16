package RenderManger;
import Scene.Fog;

public class GameScene {
    Render render;
    GameEngine engine;
    Shaders shaders;
    public GameScene(int GAME_HEIGHT, int GAME_WIDTH){
        init(GAME_HEIGHT, GAME_WIDTH);
    }
    public void init(int GAME_HEIGHT, int GAME_WIDTH){
        render = new Render();
    }
    public void RenderGameScene(long window, Fog fog){
        
    }
}
