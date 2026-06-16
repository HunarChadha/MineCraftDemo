package Items;
import Core.Mesh;
import java.util.*;
import org.joml.Vector3f;
import org.joml.Vector2i;


import Core.Entity;

public class items {
    HashMap<String, Vector2i> itemsPosition;
    HashMap<String, Vector2i> inventoryPosition;
    HashMap<Integer, Vector3f> CraftingInventory;
    List<Entity> craftedItems;
    public float[] Position = new float[]{
        -0.5f, 0.5f, -2f,
        0.5f, 0.5f, -2f,
        0.5f, -0.5f, -2f,
        -0.5f, -0.5f, -2f

    };
    public int[] indices = new int[]{
       0, 1, 2, 0, 3, 2
    };
    Mesh inventoryMesh;
    Entity inventoryEntity;
    Entity[] itemsList;

    public items(){
        itemsPosition = new HashMap<>();
        itemsList = new Entity[8];
        CraftingInventory = new HashMap<>();
        setCraftingInventory();
        craftedItems = new ArrayList<>();
        setItemPosition();
        setInventory();

    }
    public void setCraftedItems(float[] texCoords, String MeshName){
        Mesh mesh = new Mesh(Position, indices, texCoords, null, null);
        Entity entity = new Entity(mesh, null, new Vector3f(1, 0.34f, -1), MeshName, null);
        //entity.setPosition(1, 1.2f, -1);
        entity.setScale(0.1f);
        craftedItems.add(entity);
    }
    public List<Entity> getCraftedItems(){
        return this.craftedItems;
    }
    public void setItemsList(int index, Vector3f Pos, float[] texCoords, String MeshName){
        Mesh mesh = new Mesh(Position, indices, texCoords, null, null);
        itemsList[index] = new Entity(mesh, null, Pos, MeshName, null);
        itemsList[index].setScale(0.1f);
    }
    public HashMap<Integer, Vector3f> getCrafList(){
        return this.CraftingInventory;
    }
    public void setCraftingInventory(){
        Vector3f crafting = new Vector3f(0.72f, 1f, -1f);
        CraftingInventory.put(2, crafting);
    }
    public Entity getInventory(){
        return this.inventoryEntity;
    }
    public void setInventory(){
        float[] TexCoords = new float[]{
            0, 0,
            1, 0,
            1, 1,
            0, 1
        };
        inventoryMesh = new Mesh(Position, indices, TexCoords, null, null);
        inventoryEntity = new Entity(inventoryMesh, null, new Vector3f(0, 0, 0f), "inventory", null);
        inventoryEntity.setScale(50f);
    }
    public Entity[] getItemsList(){
        return this.itemsList;
    }
    public float[] calcualteTexCoords(Vector2i itemImage){
        final float x = 0.03703f;
        final float y = 0.01923f;

        float bottom = y * (float) itemImage.y;
        float Top = bottom - y;
        float right = x * (float) itemImage.x;
        float left = right - x;
        float[] TexCoords = new float[]{
            left, Top,
            right, Top,
            right, bottom,
            left, bottom 
        };
        return TexCoords;

    }
    public void setItemPosition(){
        itemsPosition.put("GrassMesh", new Vector2i(3, 1));
        itemsPosition.put("TreeMesh", new Vector2i(11, 1));

        itemsPosition.put("IronBlock", new Vector2i(16, 1));
        itemsPosition.put("IronPickaxe", new Vector2i());
        itemsPosition.put("Ironore", new Vector2i(14, 1));

        itemsPosition.put("GoldBlock", new Vector2i(17, 1));
        itemsPosition.put("GoldPickaxe", new Vector2i());
        itemsPosition.put("Goldore", new Vector2i(15, 1));

        itemsPosition.put("DiamondBlock", new Vector2i(2, 3));
        itemsPosition.put("Diamondore", new Vector2i(2, 2));
        itemsPosition.put("DiamondPickaxe", new Vector2i());

        itemsPosition.put("Plank", new Vector2i(5, 1));

    }
    public HashMap<String, Vector2i> getHashMap(){
        return this.itemsPosition;
    }
}
