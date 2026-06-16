package RenderManger;

import java.io.*;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Utils {

    static Shaders shaders = new Shaders();
    static Texture texture = new Texture();
    static Blocks blocks = new Blocks();
    static Weapons weapons = new Weapons();
    public static final String IronPickaxeMesh = "";
    public static final String IronPicakaxeTexture = "";
    public static final int GridSize = 15;
    public static final float startX = -300;
    public static final int WorldSize = 300 * 300;

    public static String readFile(String filePath){
        String normalizedPath = filePath.startsWith("/") ? filePath : "/" + filePath;
        try (InputStream is = Utils.class.getResourceAsStream(normalizedPath)) {
            if (is == null) {
                throw new RuntimeException("Resource not found on classpath: " + filePath);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException excp) {
            throw new RuntimeException("Error reading file [" + filePath + "]", excp);
        }
    }
    public List<String> ReadModel(String filename){
        List<String> ModelInfo = new ArrayList<>();
        String normalizedPath = filename.startsWith("/") ? filename : "/" + filename;
        InputStream is = Utils.class.getResourceAsStream(normalizedPath);
        if (is == null) {
            System.out.println("Resource not found on classpath: " + filename);
            return ModelInfo;
        }
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){
            String line;
            while ((line = bf.readLine()) != null){
                ModelInfo.add(line);
            }
        } catch (IOException e){
            System.out.println("Cannot find file");
            e.printStackTrace();
        }
        return ModelInfo;
    }
    public static void setBiomes(List<String> Biomes){
        Biomes.add("Jungle");
        Biomes.add("Plains");
        Biomes.add("Jungle");
        Biomes.add("Plains");
        Biomes.add("Plains");
    }
    public static int GetSize(){
        int num1 = (int)(Math.sqrt(WorldSize))/20;
        return (int) (Math.pow(num1, 2));
    }
    static class Weapons{
        public static final String DiamondPickaxeMesh = "D:\\minecraft\\minecraftDemo\\src\\main\\resources\\Models\\Weapons\\DiamondPickaxe.obj";
        public static final String WoodenPickaxeMesh = "Models\\Weapons\\WoodenPickaxe.obj";
        public static final String GoldenPickaxeMesh = "D:\\minecraft\\minecraftDemo\\src\\main\\resources\\Models\\Weapons\\GoldenPickaxe.obj";
    }
    static class Texture{
        public static String TexturePath = "Textures\\TexturesPack.png.png";
        public final String GUITexture = "Textures\\font_texture.png";
        public static final String GoldenPickaxeTexture = "Textures\\Golden Pickaxe.png";
        public static final String WoodenPickaxeTexture = "Textures\\Wooden Pickaxe.png";
        public static final String DiamondPickaxeTexture = "Textures\\DiamondPickaxe.png";
        public static final String SkyBoxNightTexture = "Textures\\SkyBoxNight.jpg";
        public static final String itemsPlacerBoxTexture = "Textures\\itemsKeeperBox.jpg";
        public static final String itemsAtlasTexture = "Textures\\itemsTextureAtlas.png";
        public static final String inventory = "Textures\\inventory.jpg";
    }
    static class Blocks{
        public static String GrassMesh = "Models\\Blocks\\Grass.obj";
        public static final String TreeMesh = "Models\\Blocks\\Trunk.obj";
        public static final String SkyBoxNightMesh = "Models\\Blocks\\SkyBlockNight.obj";
        public static final String Particles = "Models\\Blocks\\Particle.obj";
        public static final String itemsPlacerBoxMesh = "Models\\Blocks\\itemplacerBox2.obj";
    }
    static class Shaders{
        public static String FragPath = "Shaders/Frag.frag";
        public static String VertexPath = "Shaders/vert.vert";
        public static final String WeaponsVertexShader = "Shaders\\Weapons.vert";
        public static final String WeaponsFragShader = "Shaders\\Weapons.frag";
        public static final String SkyBoxVertexShader = "Shaders\\SkyBox.vert";
        public static final String SkyBoxFragShader = "Shaders\\SkyBox.frag";
        public static final String ParticlesFragShader = "Shaders\\ParticlesFrag.frag";
        public static final String ParticlesVertexShader = "Shaders\\ParticlesVert.vert";
        public static final String GameItemsFrag = "Shaders/GameItems.frag";
        public static final String GameItemsVert = "Shaders\\GameItems.vert";
        public static final String GameItemsBoxFrag = "Shaders\\GameItemsBox.frag";
        public static final String GameItemsBoxVert = "Shaders\\GameItemsBox.vert";
    }

    public static Shaders getShader(){
        return shaders;
    }
    public static Texture getTexture(){
        return texture;
    }
    public static Weapons getWeapons(){
        return weapons;
    }
    public static Blocks getBlocks(){
        return blocks;
    }
}
