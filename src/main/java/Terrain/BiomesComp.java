package Terrain;

public class BiomesComp {
    int Height;
    int TreeIntensity;
    public BiomesComp(int Height, int TreeIntensity){
        this.Height = Height;
        this.TreeIntensity = TreeIntensity;
    }
    public int getHeight(){
        return this.Height;
    }
    public int getTreeIntensity(){
        return this.TreeIntensity;
    }
    public void setBioComp(int height, int TreeIntensity){
        this.Height = height;
        this.TreeIntensity = TreeIntensity;
    }
}
