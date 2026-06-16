package Core;

import org.joml.*;
import org.joml.primitives.AABBf;
import org.joml.primitives.Intersectionf;

public class FrustumCulling {
    Vector4f[] planes;
    private final int NUM_PLANES = 6;
    Matrix4f project;
    private FrustumIntersection frustumIntersection;
    public FrustumCulling(){
        project = new Matrix4f();
        planes = new Vector4f[NUM_PLANES];
        for(int i = 0; i<NUM_PLANES; i++){
            planes[i] = new Vector4f();
        }
        frustumIntersection = new FrustumIntersection();

    }
    public void calcPlanes(Matrix4f viewMatrix, Matrix4f projection){
        project.set(projection);
        project.mul(viewMatrix);
        for(int i = 0; i<NUM_PLANES; i++){
            project.frustumPlane(i, planes[i]);
        }
    }
    public void update(){
        frustumIntersection.set(project);
    }
    public boolean isInViewFrustum(Entity entity){
        AABBf entityBonds = entity.getBoundingBox();
        if(frustumIntersection.testAab(entityBonds.minX, entityBonds.minY, entityBonds.minZ, entityBonds.maxX, entityBonds.maxY, entityBonds.maxZ)){
            return true;
        }
        return false;
    }
    public boolean calcPointDistance(Vector3f pos){
        for(Vector4f plane:planes){
            //System.out.println("Hey");
            return plane.x * pos.x + plane.y * pos.y + plane.z * pos.z  + plane.w > -0.1;
        }
        return false;
    }
    public boolean checkSelectedEntity(Camera camera, Vector3f dir, Vector3f min, Vector3f max, Vector2f nearFar){
        return Intersectionf.intersectRayAab(camera.getPLayerPos(), dir, min, max, nearFar);
    }
}
