package Model;

public class Quantities {
    int integer;
    String MeshName;
    public Quantities(){
        integer = 0;
        MeshName = "empty";
    }
    public int getQuantities(){
        return integer;
    }
    public String getMeshName(){
        return MeshName; 
    }
    public void setQuantities(int value){
        this.integer = value;
    }
    public void setMesh(String MeshName){
        this.MeshName = MeshName;
    }
}
