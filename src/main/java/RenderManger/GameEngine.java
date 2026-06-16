package RenderManger;

import java.util.List;

import org.joml.Vector3f;
import Core.Camera;
import Core.FrustumCulling;
import Core.TransFormation;
import GamePhysics.GeneralPhysics;
import GamePhysics.TerrainCollision;
import Items.Inventory;
import Items.Recipe;
import Items.items;
import Material.Material;
import Model.GameScreen;
import Scene.Fog;
import Scene.Rain;
import Scene.SkyBox;
import Terrain.GenerateTerrain;
import Weapons.Pickaxes;
import Material.DirectionalLight;
import Core.Entity;

public class GameEngine {
    public Material material;
    public SkyBox skyBox;
    public items items;
    public DirectionalLight directionalLight;
    public GameScreen screen;
    public Meshes meshes;
    public Rain rain;
    public FrustumCulling frustumCulling;
    public TransFormation transFormation;
    public Camera camera;
    public GenerateTerrain terrain;
    public GeneralPhysics physics;
    public Pickaxes pickaxes;
    TerrainCollision collision;
    Fog fog;
    Textures loadTexture;
    Inventory inventory;

    public GameEngine(int GAME_WIDTH, int GAME_HEIGHT) throws Exception{
        loadTexture = new Textures();
        setGameEngine(GAME_WIDTH, GAME_HEIGHT);
        this.inventory = new Inventory(GAME_HEIGHT, GAME_WIDTH);
    }
    public void setMatrial(){
        this.material = new Material();
        this.directionalLight = new DirectionalLight(this.material);      
    }
    public void setTrans(int width, int height){

    }
    public void setGameScreen(){
        this.meshes = new Meshes();
        this.screen = new GameScreen(this.meshes.itMesh);
        this.items = new items();
        this.collision = new TerrainCollision();
    }
    public void setGameScene(){
        Vector3f Color = new Vector3f(1, 1, 1);
        this.skyBox = new SkyBox(this.meshes.SkyBoxNightMesh);
        this.rain = new Rain(Color, this.meshes.getObjModel(), meshes.ParticlesMesh);
        this.fog = new Fog(new Vector3f(0.5f, 0.5f, 0.5f), false, 1);
    }
    public void setCalculation(int GAME_WIDTH, int GAME_HEIGHT){
        this.camera = new Camera();
        this.frustumCulling = new FrustumCulling();
        this.transFormation = new TransFormation(GAME_WIDTH, GAME_HEIGHT);
    }
    public void setTerrain(){
        this.terrain = new GenerateTerrain();

    }
    public void setGamePhysics(int GAME_HEIGHT, int GAME_WIDTH){
        this.physics = new GeneralPhysics(this.camera, this.terrain, GAME_WIDTH, GAME_HEIGHT);
    }
    public void setWeapons(){
        pickaxes = new Pickaxes();
    }
    public void setGameEngine(int GAME_WIDTH, int GAME_HEIGHT){
        setCalculation(GAME_WIDTH, GAME_HEIGHT);
        setGameScreen();
        setMatrial();
        setGameScene();
        setGamePhysics(GAME_HEIGHT, GAME_WIDTH);
        setWeapons();
        setTerrain();
    }
    public void RenderGameScene(Render render, Shaders shaders, long window, Recipe recipe){
        inventory.calcMousePos(transFormation, camera);
        Entity[][] entities = terrain.getEntities(this.meshes.GrassMesh, this.frustumCulling, this.meshes.TreeMesh, this.material, meshes.itMesh);
        List<Entity> WeaponsList = pickaxes.getEntityList(this.meshes.WoodenPickaxeMesh, frustumCulling, this.material);
        this.frustumCulling.calcPlanes(transFormation.calcViewMatrix(camera), this.transFormation.getProjectionMatrix());
        this.frustumCulling.update();
        render.RenderGameItemsPlacer(camera, window, items, transFormation, shaders.GameItemsShader, screen);
        render.RenderGameItems(transFormation, screen, items, shaders.itemsShader);
        this.camera.getGetMousePos(window);
        if(!this.screen.inventoryOpen){
            render.RenderWeapons(WeaponsList, transFormation, camera, shaders.WeaponsShader);
            render.RenderTerrain(collision, terrain, entities, frustumCulling, physics, window, fog, camera, transFormation, directionalLight, material, shaders.shaderProgram, screen, items);
            render.RenderSkyBox(camera, transFormation, fog, shaders.SkyBoxShader, skyBox);
            render.RenderParticles(rain, camera, frustumCulling, shaders.ParticlesShader, transFormation);
            render.RenderMouse();
            this.camera.MovePosition(window);
            this.camera.setGameComp(camera.cameraInc, window, terrain);
        }
        physics.setInventory(camera, window, items, screen.inventoryOpen, recipe);
    }
}
