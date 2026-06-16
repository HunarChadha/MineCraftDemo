package Terrain;

import RenderManger.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Details {
    List<String> Biomes = new ArrayList<>();
    List<String> RandomBiomes = new ArrayList<>();
    List<List<Integer>>BiomesSize = new ArrayList<>();
    int GridSize;
    int intitalSize;
    int size_of_list;
    public Details(int GridSize){
        Utils.setBiomes(Biomes);
        this.GridSize = GridSize;
        this.intitalSize = GridSize;
        setBiomes();
    }

    public void setBiomesSize(){
        List<Integer> Size = new ArrayList<>();
        Random random = new Random();
        int MaxSize = 25;
        int minSize = 15;
        int Num = random.nextInt(MaxSize - minSize + 1) + minSize;
        this.GridSize = this.GridSize - Num;
        for(int i = 0; i<2; i++){
            Size.add(Num);
        }
        BiomesSize.add(Size);
    }
    public void setBiomes(){
        while (this.GridSize >= 25){
            setBiomesSize();
            size_of_list++;
        }
        List<Integer> Size = new ArrayList<>();
        for(int i =0; i<2; i++){
            Size.add(this.GridSize);
        }
        BiomesSize.add(Size);
        setBiomesSize(BiomesSize, size_of_list + 1);
        getBioNames(3*(size_of_list + 1));
        System.out.println(BiomesSize);
    }
    public int getSize_of_list(){
        return size_of_list + 1;
    }
    public void setBiomesSize(List<List<Integer>> BiomesSize, int size_of_list){
        Random random = new Random();
        for(int i = 0; i<size_of_list; i++){
            int Size = BiomesSize.get(i).get(0);
            int num = intitalSize - Size;
            int ChunkSize = random.nextInt(11) + 15;
            List<Integer> size_Array = new ArrayList<>();
            size_Array.add(Size);
            size_Array.add(ChunkSize);
            BiomesSize.add(size_Array);
            List<Integer> size2 = new ArrayList<>();
            size2.add(Size);
            size2.add(num - ChunkSize);
            BiomesSize.add(size2);
        }
    }
    public void getBioNames(int Total_Num_index){
        int Num_of_Biomes = Biomes.size() - 1;
        Random random = new Random();
        for(int i = 0; i<Total_Num_index; i++){
            int randomIndex = random.nextInt(Num_of_Biomes + 1);
            RandomBiomes.add(Biomes.get(randomIndex));
        }
    }
    public List<List<Integer>> getBiomesSize(){
        return BiomesSize;
    }
    public List<String> getBiomes(){
        return RandomBiomes;
    }
}
