package GamePhysics;
import Core.Camera;
import Core.Entity;
import Core.FrustumCulling;
import Core.TransFormation;
import Model.GameScreen;
import Model.Quantities;
import Terrain.GenerateTerrain;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector4f;
import static org.lwjgl.glfw.GLFW.*;

import Items.Recipe;
import Items.items;
import java.util.Objects;


public class GeneralPhysics {
    public boolean Gravity = false;
    int f = 0;
    Vector3f Speed = new Vector3f(1, 0, 0);
    Vector2f MousePos = new Vector2f(0, 0);
    int Damage = 10;
    float Pos = 0;
    public GeneralPhysics(Camera camera, GenerateTerrain terrain, int GAME_WIDTH, int GAME_HEIGHT){
        calculateMousePosScreen(GAME_HEIGHT, GAME_WIDTH);
    }
    public void checkGravity(Vector3f Position){
        if(Position.y > 0){
            Gravity = true;
        }else {
            Gravity  = false;
        }
    }
    public void calculateMousePosScreen(int GAME_HEIGHT, int GAME_WIDTH){
        float MouseX = GAME_WIDTH / 2.0f;
        float MouseY = GAME_HEIGHT / 2.0f;

        float x = (2 * MouseX) / GAME_WIDTH - 1.0f;
        float y = 1.0f - (2 * MouseY) / GAME_HEIGHT;

        MousePos.set(x, y);
    }
    public Vector3f calculateModelMatrix(Camera camera, TransFormation transFormation){
        Vector4f MouseDir = new Vector4f(0, 0, 0, 0);
        float z = -1.0f;

        MouseDir.set(MousePos.x, MousePos.y, z, 1.0f);
        MouseDir.mul(transFormation.getIvProjectionMatrix());
        MouseDir.z = -1.0f;
        MouseDir.w = 0.0f;
        Matrix4f InViewMatrix = transFormation.getInviewMatrix(camera);
        MouseDir.mul(InViewMatrix);
        return new Vector3f(MouseDir.x, MouseDir.y, MouseDir.z);
    }
    public void ObjectPicking(TransFormation transFormation, Camera camera, FrustumCulling frustumCulling, Entity entity){
        Vector3f dir = calculateModelMatrix(camera, transFormation);
        entity.setSelected(false);
        Vector2f NearFar = new Vector2f();
        float closetDistance = Float.POSITIVE_INFINITY;
        if((frustumCulling.checkSelectedEntity(camera, dir, entity.getMin(), entity.getMax(), NearFar)) && closetDistance > NearFar.x){
            closetDistance = NearFar.x;
            entity.setSelected(true);
        }
    }
    public void RemoveEntity(Entity entity, Camera camera, long window, GameScreen screen, items items){
        if(camera.isButtonPresed(GLFW_MOUSE_BUTTON_LEFT, window)){
            if(entity.getSelected()){
                entity.Damage(Damage);
                if(entity.getPower() <= 0){
                    entity.setDestroyed(true);
                    if(entity.getPower() == 0){
                        for(int i = 0; i<screen.getPlavcerNum(); i++){
                            Quantities quantities = screen.getMap().get(i);
                            if(Objects.equals(quantities.getMeshName(), entity.getMeshName())){
                                int n = quantities.getQuantities();
                                n++;
                                quantities.setQuantities(n);
                                break;
                            }
                            if(Objects.equals(quantities.getMeshName(), "empty")){
                                quantities.setMesh(entity.getMeshName());
                                pickItems(entity.getMeshName(), items, i);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    public void pickItems(String MeshName, items items, int index){
        Vector3f originalPos = new Vector3f(-0.65f, -0.73f, -1.6f);
        float Pos = (float) (originalPos.x + (index * 0.15));
        Vector2i vec = items.getHashMap().get(MeshName);
        float[] texCoords = items.calcualteTexCoords(vec);

        items.setItemsList(index, new Vector3f(Pos, originalPos.y, originalPos.z), texCoords, MeshName);

    }
    public void setInventory(Camera camera, long window, items items, boolean value, Recipe recipe){
        if(value){
            Entity[] itemsList = items.getItemsList();
            for(int i = 0; i<itemsList.length; i++){
                if(itemsList[i] != null){
                    Vector3f orginalPos = new Vector3f(0.08f, 0.17f, -1);
                    if(f == 0){
                        Pos = (float) (orginalPos.x + (i * 0.03f));
                        f++;
                    }
                    itemsList[i].setPosition(Pos, orginalPos.y, orginalPos.z);
                }
            }
        }
        if(!value){
            f = 0;
        }
        if(value){
            if(camera.isKeyPressed(GLFW_KEY_B, window)){
                Entity[] itemsList = items.getItemsList();
                itemsList[0].setPosition(0.72f, 1f, -1);
                recipe.RenderCraftedItems(items);
            }
        }
    }
}
