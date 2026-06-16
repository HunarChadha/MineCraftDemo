package Core;
import Material.Material;
import org.joml.Vector3f;
import org.joml.primitives.AABBf;

public class Entity {
    float scale;
    Vector3f translate;
    Vector3f Rotation;
    Vector3f Pos;
    AABBf BoundingBox;
    Mesh mesh;
    FrustumCulling frustumCulling;
    Vector3f Position;
    boolean selected;
    boolean Destroyed;
    int Power;
    int RenderSecs;
    Material material;
    Vector3f fall;
    String MeshName;

    public Entity(Mesh mesh, FrustumCulling frustumCulling, float Scale, Vector3f Translate, String MeshName,  Material material){
        if(frustumCulling != null){
            this.frustumCulling = frustumCulling;
        }
        if(material != null){
            this.material = material;
        }
        this.scale = Scale;
        this.mesh = mesh;
        this.translate = Translate;
        this.MeshName = MeshName;
    }
    public Entity(Mesh mesh, FrustumCulling frustumCulling, Vector3f Transalte, String MeshName, Material material){
        this(mesh, frustumCulling, 1, Transalte, MeshName, material);
        this.selected = false;
        this.Destroyed = false;
        this.Rotation = new Vector3f(0, 0, 0);
        Pos = new Vector3f(-0.5f, 0, -1.6f);
        this.fall = new Vector3f(0, 0, 0);
        init(MeshName);
    }
    public void init(String MeshName){
        this.Power = CalculatePower(MeshName);
        setBoundingBox();
    }
    public Vector3f getPos(){
        return this.Pos;
    }
    public void setPosition(float x, float y, float z){
        this.Pos.x = x;
        this.Pos.y = y;
        this.Pos.z = z;
    }
    public void setRotation(float x, float y, float z){
        this.Rotation.x = x;
        this.Rotation.y = y;
        this.Rotation.z = z;
    }
    public void setDestroyed(boolean value){
        this.Destroyed = value;
    }
    public Vector3f getFall(){
        return fall;
    }
    public void setFall(){
        fall.y -= 0.2f;
    }
    public void setSelected(boolean value){
        this.selected = value;
    }
    public Vector3f getMin(){
        Vector3f min = new Vector3f();
        min.set(getTranslate());
        min.add(-getScale(), -getScale(), -getScale());
        return min;
    }
    public String getMeshName(){
        return this.MeshName;
    }
    public Vector3f getMax(){
        Vector3f max = new Vector3f();
        max.set(getTranslate());
        max.add(getScale(), getScale(), getScale());
        return max;
    }
    public boolean getSelected(){
        return this.selected;
    }
    public void setTranslate(float x, float y, float z){
        this.translate.x = x;
        this.translate.y = y;
        this.translate.z = z;

    }
    public void setScale(float scale){
        this.scale = scale;
    }
    public void setMesh(Mesh mesh){
        this.mesh = mesh;
    }
    public void setRnderSecs(){
        RenderSecs++;
    }
    public int getRenderSecs(){
        return RenderSecs;
    }
    public void render(int TexID, int ActiveTexture){
        if(!Destroyed){
            getMesh().Render(TexID, ActiveTexture);
        }
    }
    public Vector3f getPosition(){
        return Position;
    }
    public void setBoundingBox() {
        // Calculate AABB based on position and scale
        float minX = translate.x - scale;
        float minY = translate.y - scale;
        float minZ = translate.z - scale;
        float maxX = translate.x + scale;
        float maxY = translate.y + scale;
        float maxZ = translate.z + scale;

        BoundingBox = new AABBf(minX, minY, minZ, maxX, maxY, maxZ);
    }
    public Material getMaterial(){
        return this.material;
    }
    public void Damage(int value){
        this.Power -= value;
    }
    public int getPower(){
        return this.Power;
    }
    public int CalculatePower(String MeshName){
        int Power = 0;
        switch (MeshName){
            case "GrassMesh":
                Power = 1000;
                break;
            case "TreeMesh":
                Power = 3000;
                break;
        }
        return Power;
    }
    public Vector3f getTranslate(){return translate;}
    public Vector3f getRotation(){return  Rotation;}
    public float getScale(){return scale;}
    public Mesh getMesh(){return mesh;}
    public AABBf getBoundingBox(){
        return BoundingBox;
    }
}
