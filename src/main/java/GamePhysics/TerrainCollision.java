package GamePhysics;

import Core.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.joml.primitives.AABBf;

public class TerrainCollision {
    private final float radius = 1f;
    private final float height = 1.75f;
    AABBf Player;
    public TerrainCollision(){
        createPlayer();
    }
    public void checkCollision(Camera camera, TransFormation transFormation, Entity entity) {
        Matrix4f ModelViewMatrix =  transFormation.getModelViewMatrix(camera, entity.getTranslate(), entity.getRotation(), entity.getScale());
        Vector4f entityPos = new Vector4f(1, 1, 1, 1);
        entityPos.mul(ModelViewMatrix);
        AABBf enAabBf = createEntityBoundingBox(entityPos);
        if(DetectCollision(Player, enAabBf)){
            //pass
        }

    }
    public boolean checkCollisionAlongAxis(float minx1, float Maxx1, float minx2, float maxx2){
        return minx1 <= maxx2 && Maxx1 >= minx2;
    }
    public boolean DetectCollision(AABBf Player, AABBf entityBox){
        if(checkCollisionAlongAxis(Player.minX, Player.maxX, entityBox.minX, entityBox.maxX)) {
            if (checkCollisionAlongAxis(Player.minY, Player.maxY, entityBox.minY, entityBox.maxY)) {
                return checkCollisionAlongAxis(Player.minZ, Player.maxZ, entityBox.minZ, entityBox.maxZ);
            }
        }
        return false;
    }
    public void createPlayer(){
        Vector3f Pos = new Vector3f(0, 0, 0);
        float minx = Pos.x - radius;
        float miny = Pos.y - height;
        float minZ = Pos.z - radius;
        float MaxX = Pos.x + radius;
        float MaxY = Pos.y + height;
        float MaxZ = Pos.z + radius;
        Player = new AABBf(minx, miny, minZ, MaxX, MaxY, MaxZ);
    }
    public AABBf createEntityBoundingBox(Vector4f translate){
        float minX = (translate.x - 1.0f);
        float minY = (translate.y - 1.0f);
        float minZ = (translate.z - 1.0f);
        float maxX = (translate.x + 1.0f);
        float maxY = (translate.y + 1.0f);
        float maxZ = (translate.z + 1.0f);

        return new AABBf(minX, minY, minZ, maxX, maxY, maxZ);
    }
}
