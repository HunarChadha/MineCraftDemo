package Scene;

import Core.*;
import Model.ObjModel;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.*;

public  class Particles {
    List<Entity> Particles;
    Mesh mesh;
    Entity Particle;
    ObjModel Model;
    String ModelPath;
    Timer timer;
    float[] Colors;
    Vector2f[] PosArray;
    Vector3f fall;
    int secs = 0;
    Random random;
    public Particles(Vector3f Color, ObjModel Model, String ModelPath){
        this.Model = Model;
        this.ModelPath = ModelPath;
        init(Color);
    }
    public void init(Vector3f Color){
        random = new Random();
        PosArray = new Vector2f[2];
        setColors(Color);
        fall = new Vector3f(0, 0, 0);
        setMesh();
        Particles = new ArrayList<>();
    }
    public void setMesh(){
        this.mesh = this.Model.LoadModel(this.ModelPath, 0, getColors());
    }
    public void setParticle(FrustumCulling frustumCulling, Vector3f Position){
        Particle = new Entity(this.mesh, frustumCulling, new Vector3f(0, 0, 0), "Train", null);
    }
    public void setColors(Vector3f Color){
        this.Colors = new float[]{Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z, Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,
                Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z, Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,
                Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z, Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,
                Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z, Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,
                Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z, Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,
                Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z, Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,
                Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z, Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,Color.x, Color.y, Color.z,};
    }
    public float[] getColors(){
        return this.Colors;
    }
    public void createArea(Camera camera){
        float x = camera.getPLayerPos().x - 40;
        float y = camera.getPLayerPos().z - 40;
        Vector2f vec1 = new Vector2f(x, y);
        Vector2f vec2 = new Vector2f(x + 80, y + 80);
        PosArray[0] = vec1;
        PosArray[1] = vec2;
    }
    public Entity getParticle(){
        return Particle;
    }
    public Mesh getMesh(){
        return this.mesh;
    }
    public void Shower(Camera camera, FrustumCulling frustumCulling) {
        secs++;
        if(secs % 15 == 0){
            createArea(camera);
            for(int i = 0; i<400; i++){
                Vector2f vec = create_ParticlePos();
                Entity entity = new Entity(this.mesh, frustumCulling, new Vector3f(vec.x,10, vec.y), "Rain", null);
                Particles.add(entity);
            }
            secs = 1;
        }
        if(Particles.size() > 5000){
            Particles.subList(0, 600);
        }
    }
    public List<Entity> getParticles(){
        return Particles;
    }
    public Vector2f create_ParticlePos(){
        float x = random.nextFloat() *(PosArray[1].x - PosArray[0].x) + PosArray[0].x;
        float z = random.nextFloat() *(PosArray[1].y - PosArray[0].y) + PosArray[0].y;
        return new Vector2f(x,z);
    }
}
