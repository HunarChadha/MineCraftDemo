package Scene;

import Core.Camera;
import Core.Entity;
import Core.FrustumCulling;
import Model.ObjModel;
import org.joml.Vector3f;
import java.util.List;

public class Rain extends Particles{
    public Rain(Vector3f Color, ObjModel Model, String ModelPath){
        super(Color, Model, ModelPath);
    }

    @Override
    public void setParticle(FrustumCulling frustumCulling, Vector3f Position) {
        super.setParticle(frustumCulling, Position);
    }
    @Override
    public float[] getColors(){
        return super.getColors();
    }

    @Override
    public Entity getParticle(){
        return super.getParticle();
    }
    @Override
    public List<Entity> getParticles(){
        return super.getParticles();
    }
    @Override
    public void Shower(Camera camera, FrustumCulling frustumCulling){
        super.Shower(camera, frustumCulling);
    }
}
