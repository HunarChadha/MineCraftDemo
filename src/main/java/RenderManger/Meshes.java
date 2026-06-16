package RenderManger;
import Core.Mesh;
import Model.ObjModel;

public class Meshes {
    ObjModel Loader;
    public Mesh TreeMesh;
    public Mesh GrassMesh;
    public Mesh DiamondPickaxeMesh;
    public Mesh WoodenPickaxeMesh;
    public Mesh SkyBoxNightMesh;
    public String ParticlesMesh;
    public String itemsPlacerBoxMesh;
    public Mesh itMesh;

    public Meshes(){
        Loader = new ObjModel();
        TreeMesh = Loader.LoadModel(Utils.Blocks.TreeMesh, 1, null);
        GrassMesh = Loader.LoadModel(Utils.Blocks.GrassMesh, 1, null);
        //DiamondPickaxeMesh = Loader.LoadModel(Utils.Weapons.DiamondPickaxeMesh);
        WoodenPickaxeMesh = Loader.LoadModel(Utils.Weapons.WoodenPickaxeMesh, 1, null);
        SkyBoxNightMesh = Loader.LoadModel(Utils.Blocks.SkyBoxNightMesh, 1, null);
        itMesh = Loader.LoadModel(Utils.Blocks.itemsPlacerBoxMesh, 1, null);
        ParticlesMesh = Utils.Blocks.Particles;
        itemsPlacerBoxMesh = Utils.Blocks.itemsPlacerBoxMesh;
    }
    public ObjModel getObjModel(){
        return Loader;
    }
}
