package RenderManger;
import java.awt.image.BufferedImage;

import Core.Texture;

public class Textures {
    Texture texture;
    public int TextureAtlas;
    public int DiamondPickaxeTexture;
    public int WoodenPickaxeTexture;
    public int GoldenPickaxeTexture;
    public int SkyBoxNightTexture;
    public int itemsPlacerBoxTexture;
    public int itemsTextureAtlas;
    public int inventoryTexture;
    public Textures(){
        texture = new Texture();
        TextureAtlas = loadTexture(Utils.Texture.TexturePath);
        WoodenPickaxeTexture = loadTexture(Utils.Texture.WoodenPickaxeTexture);
        DiamondPickaxeTexture = loadTexture(Utils.Texture.DiamondPickaxeTexture);
        GoldenPickaxeTexture = loadTexture(Utils.Texture.GoldenPickaxeTexture);
        SkyBoxNightTexture = loadTexture(Utils.Texture.SkyBoxNightTexture);
        itemsPlacerBoxTexture = loadTexture(Utils.Texture.itemsPlacerBoxTexture);
        itemsTextureAtlas = loadTexture(Utils.Texture.itemsAtlasTexture);
        inventoryTexture = loadTexture(Utils.Texture.inventory);

        }
        public int loadTexture(String TexturePaths){
            BufferedImage image = texture.loadImage(TexturePaths);
            return texture.loadTexture(image);
        }
}
