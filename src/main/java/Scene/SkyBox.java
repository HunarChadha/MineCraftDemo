package Scene;
import Core.Entity;
import Core.Mesh;
import Material.Material;
import org.joml.Vector3f;
import java.lang.Math;

public class SkyBox {
    Entity SkyBoxEntity;
    Material material;
    float Factor;
    public SkyBox(Mesh mesh){
        setSkyBoxEntity(mesh);
        material = new Material();
        init();
    }
    public void init(){
        calcFog();
    }
    public void setSkyBoxEntity(Mesh mesh){
        SkyBoxEntity = new Entity(mesh, null, new Vector3f(0, 0, 0), "SkyBox", material);
    }
    public Entity getSkyBoxEntity(){
        return SkyBoxEntity;
    }
    public void calcFog(){
        float Distance = 10;
        float fogFactor = (float) ((float) 1.0 / Math.exp( (Distance * 0.14) * (Distance * 0.14)));
        fogFactor = (float) Math.max(0.0, Math.min(1.0, fogFactor));
        this.Factor = fogFactor;
    }
    public float getFactor(){
        return this.Factor;
    }
}
