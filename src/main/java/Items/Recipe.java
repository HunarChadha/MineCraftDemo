package Items;

import java.util.List;
import java.util.Objects;
import org.joml.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;
import Core.Entity;
import RenderManger.Utils;

public class Recipe {
    Utils utils;
    List<String> CraftingRecipe;
    String[][] BrokenList;
    public Recipe(){
        CraftingRecipe = new ArrayList<>();
        utils = new Utils();
    }
    public Recipe(String filename){
        this();
        ReadRecipe(filename);
        System.out.println(CraftingRecipe);
    }
    public void ReadRecipe(String fileName){
        List<String> cList = utils.ReadModel(fileName);
        BrokenList = new String[cList.size()][10];
        for(int i = 0; i<cList.size(); i++){
            String[] a = cList.get(i).split("/");
            for(int f = 0; f<a.length; f++){
                BrokenList[i][f] = a[f];
            }
        }
    }
    public void RenderCraftedItems(items items){
        for(int n = 0; n<BrokenList.length; n++){
            Entity[] itemsList = items.getItemsList();
            HashMap<Integer, Vector3f> craftList = items.getCrafList();
            String[] a = BrokenList[n][3].split(",");
            for(int i = 0; i<itemsList.length; i++){
                if(itemsList[i] != null){
                    for(int e = 0; e<4;e++){
                        if(itemsList[i].getPos().equals(craftList.get(Integer.parseInt(a[e])))){
                            System.out.println(itemsList[i].getMeshName());
                            if(Objects.equals(itemsList[i].getMeshName(), BrokenList[i][4])){
                                String MeshName = BrokenList[i][2];
                                items.setCraftedItems(items.calcualteTexCoords(items.getHashMap().get(MeshName)), MeshName);
                            }
                        } 
                    }
                }
            }
        }
    }
}
