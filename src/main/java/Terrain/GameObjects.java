package Terrain;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

import java.util.*;

public class GameObjects {
    Random random;
    BiomesComp[] biomesComps;
    public GameObjects(int Length){
        random = new Random();
        biomesComps = new BiomesComp[Length];
    }
    public void Render(List<Float>TopPosition, List<Vector3f> TreePos, List<Vector2f> Pos, int intensity){
        int index = 0;
        while (index <= 399){
            index = index + getRandom(intensity);
            if(index < 399){
                for(float y = TopPosition.get(index) + 2; y<TopPosition.get(index) + 18; y+= 2){
                    TreePos.add(new Vector3f(Pos.get(index).x, y, Pos.get(index).y));
                }
            }
        }
    }
    public int getRandom(int intensity){
        int MaxSize;
        int minsize;
        switch (intensity){
            case 5:
                MaxSize = 65;
                minsize = 35;
                break;
            case 4:
                MaxSize = 450;
                minsize = 390;
                break;
            case 3:
                MaxSize = 450;
                minsize = 397;
                break;
            default:
                MaxSize = 200;
                minsize = 100;
                break;
        }
        return random.nextInt(MaxSize - minsize - 1) + minsize;
    }
    public void checkIntensity(int index, List<HashMap<Vector2i, String>> hashMaps){
        HashMap<Vector2i , String> map = hashMaps.get(index);
        for (Map.Entry<Vector2i, String> entry: map.entrySet()){
            switch (entry.getValue()){
                case "Jungle":
                    biomesComps[index] = new BiomesComp(10, 5);
                    break;
                case "Plains":
                    biomesComps[index] = new BiomesComp(10, 4);
                    break;
                case "Mountains":
                    biomesComps[index] = new BiomesComp(50, 3);
                    break;
            }
        }
    }

}
