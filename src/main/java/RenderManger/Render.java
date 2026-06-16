package RenderManger;

import Items.items;
import Material.DirectionalLight;
import Material.Material;
import Model.GameScreen;
import Scene.Fog;
import Scene.Rain;
import Scene.SkyBox;
import Terrain.GenerateTerrain;
import Core.*;
import GamePhysics.GeneralPhysics;
import GamePhysics.TerrainCollision;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static org.lwjgl.opengl.GL13.GL_TEXTURE4;
import static org.lwjgl.opengl.GL13.GL_TEXTURE5;
import static org.lwjgl.opengl.GL11.*;
import java.util.List;
import org.joml.Matrix4f;

public class Render {
    Textures loadTexture;

    public Render(){
        loadTexture = new Textures();
    }
    public void RenderSkyBox(Camera camera, TransFormation transFormation, Fog fog, ShaderProgram SkyBoxShader, SkyBox skyBox){
        SkyBoxShader.bind();
        SkyBoxShader.setUniforms("ProjectionMatrix", transFormation.getProjectionMatrix());
        SkyBoxShader.setUniforms("WorldMatrix", transFormation.setWorldMatrix(skyBox.getSkyBoxEntity().getTranslate(), skyBox.getSkyBoxEntity().getRotation(), skyBox.getSkyBoxEntity().getScale()));
        Matrix4f viewMatrix = transFormation.calcViewMatrix(camera);
        viewMatrix.m30(0);
        viewMatrix.m31(0);
        viewMatrix.m32(0);
        SkyBoxShader.setFogUniforms("fog", fog);
        SkyBoxShader.setUniforms("FogFactor", skyBox.getFactor());
        SkyBoxShader.setUniforms("viewMatrix", viewMatrix);
        SkyBoxShader.setUniforms("Texture", 2);
        skyBox.getSkyBoxEntity().render(loadTexture.SkyBoxNightTexture, GL_TEXTURE2);
        SkyBoxShader.unbind();
    }
    public void RenderGameItems(TransFormation transFormation, GameScreen screen, items items, ShaderProgram itemsShader){
        itemsShader.bind();
        itemsShader.setUniforms("texture_sampler", 0);
        itemsShader.setUniforms("projectionMatrix", transFormation.getProjectionMatrix());
        itemsShader.setUniforms("InventoryOpen", screen.inventoryOpen ? 1 : 0);
        for(Entity entity: items.getItemsList()){
            if(entity != null){
                itemsShader.setUniforms("ModelMatrix", transFormation.setWorldMatrix(entity.getTranslate(), entity.getRotation(),entity.getScale()));
                itemsShader.setUniforms("viewMatrix", transFormation.getTranslation(entity.getPos()));
                entity.render(loadTexture.itemsTextureAtlas, GL_TEXTURE0);
            }
        }
        for(Entity entity: items.getCraftedItems()){
            if(entity != null){
                itemsShader.setUniforms("ModelMatrix", transFormation.setWorldMatrix(entity.getTranslate(), entity.getRotation(),entity.getScale()));
                itemsShader.setUniforms("viewMatrix", transFormation.getTranslation(entity.getPos()));
                entity.render(loadTexture.itemsTextureAtlas, GL_TEXTURE0);
            }
        }
        itemsShader.unbind();
    }
    public void RenderParticles(Rain rain, Camera camera, FrustumCulling frustumCulling, ShaderProgram ParticlesShader, TransFormation transFormation){
        ParticlesShader.bind();
        rain.Shower(camera, frustumCulling);
        ParticlesShader.setUniforms("ProjectionMatrix", transFormation.getProjectionMatrix());
        for(Entity entity: rain.getParticles()){
            entity.setFall();
            entity.setRnderSecs();
            if(entity.getRenderSecs() < 600){
                ParticlesShader.setUniforms("ModelViewMatrix", transFormation.getParticlesModelViewMatrix(camera, entity.getTranslate(), entity.getRotation(), entity.getScale(), entity.getFall()));
                entity.render(100, 100);
                
            }
        }
        ParticlesShader.unbind();
    }
    public void RenderWeapons(List<Entity> entities, TransFormation transFormation, Camera camera, ShaderProgram WeaponsShader){
        WeaponsShader.bind();
        WeaponsShader.setUniforms("WeaponsProjectionMatrix", transFormation.getProjectionMatrix());
        WeaponsShader.setUniforms("Texture", 1);
        for(Entity entity: entities){
            WeaponsShader.setUniforms("WeaponsWorldMatrix", transFormation.setWeaponsWorldMatrix(entity.getTranslate(), entity.getRotation(), entity.getScale()));
            WeaponsShader.setUniforms("WeaponsViewMatrix", transFormation.calcViewMatrix(camera));
            entity.render(loadTexture.WoodenPickaxeTexture, GL_TEXTURE1);
        }
        //System.out.println(dir);
        WeaponsShader.unbind();
    }
    public void RenderTerrain(TerrainCollision collision, GenerateTerrain terrain, Entity[][] entities, FrustumCulling frustumCulling, GeneralPhysics physics, long window, Fog fog, Camera camera, TransFormation transFormation, DirectionalLight directionalLight, Material material, ShaderProgram shaderProgram, GameScreen screen, items items){
        shaderProgram.bind();
        terrain.createTerrain3D(camera);
        terrain.RenderChunks();
        directionalLight.ChangeDirection();
        transFormation.LightDirView(directionalLight, camera);
        shaderProgram.setUniforms("projection", transFormation.getProjectionMatrix());
        shaderProgram.setUniforms("texture_sampler", 5);
        shaderProgram.setFogUniforms("fog", fog);
        for (int i = 0; i<terrain.visibleChunks.length;i++){
            if(terrain.visibleChunks[i]){
                for(Entity entity: entities[i]) {
                    if (entity != null) {
                        if(frustumCulling.isInViewFrustum(entity)){
                            shaderProgram.setUniforms("modelViewMatrix", transFormation.getModelViewMatrix(camera, entity.getTranslate(), entity.getRotation(), entity.getScale()));
                            shaderProgram.setUniforms("AmbientLight", entity.getMaterial().getAmbinetLight());
                            shaderProgram.setDiffuseUniforms("DiffuseComp", material);
                            physics.ObjectPicking(transFormation, camera, frustumCulling, entity);
                            shaderProgram.setUniforms("Selected", entity.getSelected() ? 1 : 0);
                            physics.RemoveEntity(entity, camera, window, screen, items);
                            collision.checkCollision(camera, transFormation, entity);
                            entity.render(loadTexture.TextureAtlas, GL_TEXTURE5);
                        }
                    }
                }
            }
        }
        shaderProgram.unbind();
    }
    public void RenderGameItemsPlacer(Camera camera, long window,items items, TransFormation transFormation, ShaderProgram GameItemsShader, GameScreen screen){
        GameItemsShader.bind();
        GameItemsShader.setUniforms("ProjectionMatrix", transFormation.getProjectionMatrix());
        GameItemsShader.setUniforms("texture_sampler", 4);
        screen.openInventory(camera, window);
        if(!screen.inventoryOpen){
            screen.setSelected(camera, window);
            for(Entity entity: screen.getEntities()){
                GameItemsShader.setUniforms("ModelMatrix", transFormation.setWorldMatrix(entity.getTranslate(), entity.getRotation(), entity.getScale()));
                GameItemsShader.setUniforms("selected", entity.getSelected() ? 1 : 0);
                entity.render(loadTexture.itemsPlacerBoxTexture, GL_TEXTURE4);
        }
        }
        if(screen.inventoryOpen){
            GameItemsShader.setUniforms("ModelMatrix", transFormation.setWorldMatrix(items.getInventory().getTranslate(), items.getInventory().getRotation(), items.getInventory().getScale()));
            items.getInventory().render(loadTexture.inventoryTexture, GL_TEXTURE4);
        }
        GameItemsShader.unbind();
    }
    public void RenderMouse(){
        glBegin(GL_QUADS);
        glVertex2f(-0.001f, 0.045f);
        glVertex2f(-0.001f, -0.045f);
        glVertex2f(0.001f, -0.045f);
        glVertex2f(0.001f ,0.045f);

        glVertex2f(-0.045f, 0.001f);
        glVertex2f(-0.045f, -0.001f);
        glVertex2f(0.045f, -0.001f);
        glVertex2f(0.045f ,0.001f);
        glEnd();
    }
}
