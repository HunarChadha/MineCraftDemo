package Weapons;
import Core.Entity;
import Core.Mesh;
import Core.ShaderProgram;
import java.util.ArrayList;
import java.util.List;
import Core.FrustumCulling;
import Material.Material;
import org.joml.Vector3f;

public class Pickaxes {
    ShaderProgram PickaxeShaders;
    List<Entity> entityList;
    boolean Add = true;
    public Pickaxes(){
        entityList = new ArrayList<>();
    }
    public void setWeaponShader()throws Exception{
        PickaxeShaders = new ShaderProgram();
    }
    public List<Entity> getEntityList(Mesh mesh, FrustumCulling frustumCulling, Material material){
        if(Add){
            entityList.add(new Entity(mesh, frustumCulling, new Vector3f(0.2f, 0, -10), "Weapons", material));
            Add = false;
        }
        return entityList;
    }
    public ShaderProgram getPickageShaders(){
        return PickaxeShaders;
    }
}
