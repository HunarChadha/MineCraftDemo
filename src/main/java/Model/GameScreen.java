package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.*;
import Core.Camera;
import Core.Entity;
import Core.Mesh;

public class GameScreen {
    List<Entity> itemsPlacerList = new ArrayList<>();
    int[] buttons;
    public boolean inventoryOpen;
    HashMap<Integer, Quantities> itemsData;
    HashMap<String, Integer> quantities;
    final int Num_OF_Placer = 10;
    Mesh mesh;
    int n = 0;
    int f = 0;
    public GameScreen(Mesh mesh){
        this.mesh = mesh;
        inventoryOpen = false;
        setEntityList();
        itemsData = new HashMap<>();
        setItemsData();
        setButtons();
    }
    public void setEntityList(){
        float x = -0.7f;
        for(int i = 0; i<Num_OF_Placer; i++){
            Entity entity = new Entity(mesh, null, new Vector3f(x, -0.8f, -2), "items", null);
            entity.setScale(0.4f);
            entity.setRotation(-19, -90, 0);
            itemsPlacerList.add(entity);
            x += 0.16f;
        }
    }
    public void setButtons(){
        buttons = new int[]{
            GLFW_KEY_0, GLFW_KEY_1,GLFW_KEY_2,GLFW_KEY_3,GLFW_KEY_4,GLFW_KEY_5,GLFW_KEY_6,GLFW_KEY_7,GLFW_KEY_8,GLFW_KEY_9
        };
    }
    public void setItemsData(){
        for(int i = 0; i<Num_OF_Placer; i++){
            Quantities quantities = new Quantities();
            itemsData.put(i, quantities);
        }
    }
    public void setMap(int index, Quantities quantities){
        itemsData.replace(index, quantities);
    }
    public HashMap<Integer, Quantities> getMap(){
        return itemsData;
    }
    public List<Entity> getEntities(){
        return this.itemsPlacerList;
    }
    public void setAllFalse(){
        for(Entity entity: itemsPlacerList){
            entity.setSelected(false);
        }
    }
    public void setSelected(Camera camera, long window){
        for(int i = 0; i<Num_OF_Placer; i++){
            if(camera.isKeyPressed(buttons[i], window)){
                setAllFalse();
                itemsPlacerList.get(i).setSelected(true);
            }
        }
    }
    public int getPlavcerNum(){
        return Num_OF_Placer;
    }
    public void openInventory(Camera camera, long window){
        if(camera.isKeyPressed(GLFW_KEY_F, window) && n == 0){
            if(inventoryOpen && f == 0){
                inventoryOpen = false;
                f++;
            }
            if(!inventoryOpen && f == 0){
                inventoryOpen = true;
                f++;
            }
            n++;
        }
        if(camera.isKeyReleased(GLFW_KEY_F, window)){
            n = 0;
            f = 0;
        }
    }

}
