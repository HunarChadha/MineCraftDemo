package Terrain;
import Core.*;
import RenderManger.Utils;
import Material.Material;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector2i;
import java.math.BigDecimal;
import java.util.*;

import org.joml.Vector4f;

public class GenerateTerrain{
    Entity[][] entities;
    public boolean[] visibleChunks;
    public Vector3f[][] Pos2DArray;
    Details details;
    float[][] Distance;
    Entity[][] EntityPos;
    Material material;
    GameObjects GameObjects;
    public Vector4f[][] ArrayPos4D;
    public int CurrentChunkNumber = 0;
    String[][] Meshes;
    Vector3f[][]Position;
    List<HashMap<Vector2i, String>> Biomes_All_info = new ArrayList<>();
    public GenerateTerrain(){
        init();
        setTerrainDetails(setSizeOFBiomes(details), details);
        CreateChunks2DArray();
    }
    public void init(){
        //info = new GridInfo(Utils.GridSize);
        details = new Details(Utils.GridSize);
        int Size = 12000;
        GameObjects = new GameObjects(Utils.GetSize());
        visibleChunks = new boolean[Utils.GetSize()];
        entities = new Entity[visibleChunks.length][Size];
        Position = new Vector3f[visibleChunks.length][Size];
        Pos2DArray = new Vector3f[visibleChunks.length][1];
        Distance = new float[visibleChunks.length][1];
        EntityPos = new Entity[visibleChunks.length][1];
        ArrayPos4D = new Vector4f[visibleChunks.length][Size];
        Meshes = new String[visibleChunks.length][Size];
    }
    public Entity[][] getEntities(Mesh mesh, FrustumCulling frustumCulling, Mesh TreeMesh, Material material, Mesh ItemsMesh){
        for(int i = 0; i<visibleChunks.length; i++){
            if(visibleChunks[i]){
                for(int n = 0; n<Position[i].length; n++){
                    if(Position[i][n] != null){
                        if(entities[i][n] == null){
                            if(Objects.equals(Meshes[i][n], "GrassMesh")){
                                entities[i][n] = new Entity(mesh, frustumCulling, new Vector3f(Position[i][n].x, Position[i][n].y-10, Position[i][n].z), "GrassMesh", material);
                            }
                            if(Objects.equals(Meshes[i][n], "TreeMesh")){
                                entities[i][n] = new Entity(TreeMesh, frustumCulling, new Vector3f(Position[i][n].x, Position[i][n].y-10, Position[i][n].z), "TreeMesh", material);
                            }
                        }
                    }
                }
            }
        }
        return entities;
    }
    public Entity[][] getEntities2(Mesh mesh, FrustumCulling frustumCulling, Material material, int N){
        if(N == 1){
            entities[0][0] = new Entity(mesh, frustumCulling, new Vector3f(-0.2f, -0.2f, -2), "GrassMesh", material);
            N = 2;
        }
        return entities;
    }
    public void CreateChunks2DArray(){
        for(int i = 0; i<Pos2DArray.length; i++) {
            Vector2f vec1 = (generatePos2D(Biomes_All_info.get(i).keySet().iterator().next(), Utils.startX, Utils.startX));
            Vector3f vec5 = new Vector3f(vec1.x + 20, 0, vec1.y + 20);
            Pos2DArray[i][0] = vec5;
            //entities[i][4999] = new Entity(mesh, frustumCulling, Scale, Pos2DArray[i][0]);
        }
    }
    public void update(float distance, int index){
        Distance[index][0] = distance;
    }
    public List<Vector2f> generateArray2D(Vector2f Pos2D){
        List<Vector2f> Pos2DArray = new ArrayList<>();
        String startX = String.valueOf(Pos2D.x);
        String endX = String.valueOf(Pos2D.x + 39.9);

        String szz = String.valueOf(Pos2D.y);
        String ezz = String.valueOf(Pos2D.y + 39.9);

        BigDecimal startZ = new BigDecimal(szz);
        BigDecimal endZ = new BigDecimal(ezz);

        BigDecimal start = new BigDecimal(startX);
        BigDecimal end = new BigDecimal(endX);


        BigDecimal increment = new BigDecimal("2");
        for(BigDecimal x = start; x.compareTo(end)<=0; x = x.add(increment)) {
            for (BigDecimal z = startZ; z.compareTo(endZ) <= 0; z = z.add(increment)) {
                float xPos = x.floatValue();
                float zPos = z.floatValue();
                Pos2DArray.add(new Vector2f(xPos, zPos));
            }
        }
        return Pos2DArray;
    }
    public Vector2f generatePos2D(Vector2i GridNumber, float startX, float startXY){
        float xPos = startX + (40 * GridNumber.x);
        float zPos = startXY + (40 * GridNumber.y);
        return new Vector2f(xPos, zPos);
    }
    public void createTerrain3D(Camera camera){
        for(int i = 0; i<Pos2DArray.length; i++){
            for(int n = 0; n<4; n++){
                update(camera.CalculateDistance(Pos2DArray[i][0], camera.getPLayerPos()), i);
                if(Distance[i][0] < 40){
                    CurrentChunkNumber = i;
                }
                if(Distance[i][0] < 120){
                    visibleChunks[i] = true;
                }
                if(Distance[i][0] > 120){
                    visibleChunks[i] = false;
                }
            }
        }
    }
    public void RenderChunks(){
        for(int i = 0; i<visibleChunks.length; i++){
            if(visibleChunks[i]){
                if(Position[i][0] == null){
                    createChunks(generateArray2D(generatePos2D(Biomes_All_info.get(i).keySet().iterator().next(), Utils.startX, Utils.startX)), i, Biomes_All_info);
                }
            }
        }
    }
    public Entity[][] getEntities2(Mesh mesh, FrustumCulling frustumCulling){
        /*List<Vector3f> chunks = new ArrayList<>();
        for(float x = -20f;x<20;x+= 2){
            for (float z = -20f; z<20; z+= 2){
                chunks.add(new Vector3f(x, 0, z));
            }
        }
        for (int i = 0; i<chunks.size(); i++){
            entities[0][i] = new Entity(mesh, frustumCulling, Scale, chunks.get(i));
        }*/
        return entities;
    }

    public void createChunks(List<Vector2f> PosArray, int index, List<HashMap<Vector2i, String>> hashMaps){
        GameObjects.checkIntensity(index, hashMaps);
        Perlin perlin1 = new Perlin(GameObjects.biomesComps[index].getHeight());
        List<Float> elevation = perlin1.getElevation();
        List<Vector3f> chunks = new ArrayList<>();
        List<Vector3f> TreePos = new ArrayList<>();
        int n = 0;
        List<Float> TopPosition = new ArrayList<>();
        while (n != elevation.size()){
            float Maxvalue = 0;
            float var = (float) Math.ceil(elevation.get(n));
            for(float y = 0; y<var; y+= 2){
                chunks.add(new Vector3f(PosArray.get(n).x, y, PosArray.get(n).y));
                TopPosition.add(y);
            }
            for(float value: TopPosition){
                if(value > Maxvalue){
                    Maxvalue = value;
                }
            }
            TopPosition.add(Maxvalue);
            n++;
        }
        GameObjects.Render(TopPosition, TreePos, PosArray, GameObjects.biomesComps[index].getTreeIntensity());
        for(int i = 0; i<chunks.size(); i++){
            Position[index][i] = chunks.get(i);
            Meshes[index][i] = "GrassMesh";
        }
        for (int i = 0; i<TreePos.size(); i++){
            Position[index][i + chunks.size()] = TreePos.get(i);
            Meshes[index][i + chunks.size()] = "TreeMesh";
        }
    }
    public int[][] setSizeOFBiomes(Details details){
        List<List<Integer>> BiomesSize = details.getBiomesSize();
        int[][] indices = new int[3*details.getSize_of_list()][4];
        int minus = details.getSize_of_list();
        for(int i = 0; i<3*details.getSize_of_list(); i++){
            if(i<details.getSize_of_list()){
                if(i == 0){
                    indices[0][0] = 0;
                    indices[0][1] = BiomesSize.get(0).get(0);
                    indices[0][2] = 0;
                    indices[0][3] = BiomesSize.get(0).get(0);
                }
                if(i != 0){
                    indices[i][0] = indices[i-1][1];
                    indices[i][1] = indices[i][0] + BiomesSize.get(i).get(0);
                    indices[i][2] = 0;
                    indices[i][3] = BiomesSize.get(i).get(0);
                }
            }
            if(i>=details.getSize_of_list()){
                if(details.getSize_of_list() % 2 != 0){
                    if(i % 2 != 0){
                        indices[i][0] = indices[i - minus][0];
                        indices[i][1] = indices[i - minus][1];
                        indices[i][2] = indices[i - minus][3];
                        indices[i][3] = indices[i - minus][3] + BiomesSize.get(i).get(1);
                        indices[i + 1][0] = indices[i][0];
                        indices[i + 1][1] = indices[i][1];
                        indices[i + 1][2] = indices[i][3];
                        indices[i + 1][3] = indices[i][3] + BiomesSize.get(i + 1).get(1);
                        minus++;
                    }
                }
                if(details.getSize_of_list() % 2 == 0){
                    if(i % 2 == 0){
                        indices[i][0] = indices[i - minus][0];
                        indices[i][1] = indices[i - minus][1];
                        indices[i][2] = indices[i - minus][2];
                        indices[i][3] = indices[i - minus][3] + BiomesSize.get(i).get(1);
                        indices[i + 1][0] = indices[i][0];
                        indices[i + 1][1] = indices[i][1];
                        indices[i + 1][2] = indices[i][3];
                        indices[i + 1][3] = indices[i][3] + BiomesSize.get(i + 1).get(1);
                        minus++;
                    }
                }
            }
        }
        return indices;
    }
    public void setTerrainDetails(int[][] indices, Details details){
        List<String> BiommesName = details.getBiomes();
        for(int i = 0; i<indices.length; i++){
            int x = indices[i][0];
            while(x != indices[i][1]){
                for(int y = indices[i][2]; y<indices[i][3]; y++){
                    Vector2i vec = new Vector2i(x, y);
                    HashMap<Vector2i, String> hashMap = new HashMap<>();
                    hashMap.put(vec, BiommesName.get(i));
                    Biomes_All_info.add(hashMap);
                }
                x++;
            }
        }
    }
}
