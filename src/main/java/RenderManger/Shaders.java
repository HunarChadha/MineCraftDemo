package RenderManger;

import Core.ShaderProgram;

public class Shaders {
    public ShaderProgram WeaponsShader;
    public ShaderProgram SkyBoxShader;
    public ShaderProgram ParticlesShader;
    public ShaderProgram shaderProgram;
    public ShaderProgram itemsShader;
    public ShaderProgram GameItemsShader;

    public Shaders() throws Exception{
        createGameItemsShader();
        createShaderProgram();
        createParticlesShader();
        createSkyBoxShader();
        createWeaponsShader();
        createItemsShader();
    }
    public void createItemsShader()throws Exception{
        itemsShader = new ShaderProgram();
        itemsShader.createVertexShader(Utils.readFile(Utils.Shaders.GameItemsVert));
        itemsShader.createFragShader(Utils.readFile(Utils.Shaders.GameItemsFrag));
        itemsShader.link();
        itemsShader.createUniforms("projectionMatrix");
        itemsShader.createUniforms("ModelMatrix");
        itemsShader.createUniforms("texture_sampler");
        itemsShader.createUniforms("viewMatrix");
        itemsShader.createUniforms("InventoryOpen");

    }
    public void createShaderProgram() throws Exception{
        shaderProgram = new ShaderProgram();
        shaderProgram.createFragShader(Utils.readFile(Utils.Shaders.FragPath));
        shaderProgram.createVertexShader(Utils.readFile(Utils.Shaders.VertexPath));
        shaderProgram.link();
        shaderProgram.createUniforms("projection");
        shaderProgram.createUniforms("modelViewMatrix");
        shaderProgram.createUniforms("texture_sampler");
        shaderProgram.createUniforms("Selected");
        shaderProgram.createUniforms("AmbientLight");
        shaderProgram.createDiffuseUniforms("DiffuseComp");
        shaderProgram.createFogUniforms("fog");
    }
    public void createGameItemsShader() throws Exception{
        GameItemsShader = new ShaderProgram();
        GameItemsShader.createVertexShader(Utils.readFile(Utils.Shaders.GameItemsBoxVert));
        GameItemsShader.createFragShader(Utils.readFile(Utils.Shaders.GameItemsBoxFrag));
        GameItemsShader.link();
        GameItemsShader.createUniforms("ProjectionMatrix");
        GameItemsShader.createUniforms("ModelViewMatrix");
        GameItemsShader.createUniforms("texture_sampler");
        GameItemsShader.createUniforms("ModelMatrix");
        GameItemsShader.createUniforms("texture_sampler");
        GameItemsShader.createUniforms("selected");
    }
    public void createWeaponsShader() throws Exception{
        WeaponsShader = new ShaderProgram();
        WeaponsShader.createVertexShader(Utils.readFile(Utils.Shaders.WeaponsVertexShader));
        WeaponsShader.createFragShader(Utils.readFile(Utils.Shaders.WeaponsFragShader));
        WeaponsShader.link();
        WeaponsShader.createUniforms("Texture");
        WeaponsShader.createUniforms("WeaponsWorldMatrix");
        WeaponsShader.createUniforms("WeaponsProjectionMatrix");
        WeaponsShader.createUniforms("WeaponsViewMatrix");
    }
    public void createParticlesShader() throws Exception{
        ParticlesShader = new ShaderProgram();
        ParticlesShader.createVertexShader(Utils.readFile(Utils.Shaders.ParticlesVertexShader));
        ParticlesShader.createFragShader(Utils.readFile(Utils.Shaders.ParticlesFragShader));
        ParticlesShader.link();
        ParticlesShader.createUniforms("ProjectionMatrix");
        ParticlesShader.createUniforms("ModelViewMatrix");
        ParticlesShader.createUniforms("TranslationMatrix");
    }
    public void createSkyBoxShader() throws Exception{
        SkyBoxShader = new ShaderProgram();
        SkyBoxShader.createVertexShader(Utils.readFile(Utils.Shaders.SkyBoxVertexShader));
        SkyBoxShader.createFragShader(Utils.readFile(Utils.Shaders.SkyBoxFragShader));
        SkyBoxShader.link();
        SkyBoxShader.createUniforms("WorldMatrix");
        SkyBoxShader.createUniforms("Texture");
        SkyBoxShader.createUniforms("ProjectionMatrix");
        SkyBoxShader.createUniforms("viewMatrix");
        SkyBoxShader.createFogUniforms("fog");
        SkyBoxShader.createUniforms("FogFactor");
    }
    public ShaderProgram getShaderProgram(){
        return shaderProgram;
    }
    public ShaderProgram getWeaponShader(){
        return WeaponsShader;
    }
    public ShaderProgram getParticlesShader(){
        return ParticlesShader;
    }
    public ShaderProgram getSkyBoxShader(){
        return SkyBoxShader;
    }
    public ShaderProgram getItemShader(){
        return itemsShader;
    }
    public ShaderProgram getGameItemsShader(){
        return GameItemsShader;
    }
    public void cleanUp(){
        shaderProgram.cleanUp();
        GameItemsShader.cleanUp();
        WeaponsShader.cleanUp();
        itemsShader.cleanUp();
        SkyBoxShader.cleanUp();

    }

}
