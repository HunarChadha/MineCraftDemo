package Core;

import Material.DirectionalLight;
import org.joml.*;

import java.lang.Math;

public class TransFormation {
    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Matrix4f IvProjectionMatrix;
    private final float FOV = (float) Math.toRadians(60.f);
        float z_near = 0.01f;
        float z_Far = 1000.0f;

    public TransFormation(int GAME_WIDTH, int GAME_HEIGHT){
        setProjectionMatrix(GAME_HEIGHT, GAME_WIDTH);
        calcIvProjection(GAME_WIDTH, GAME_HEIGHT);
    }
    public void setProjectionMatrix(int GAME_HEIGHT, int GAME_WIDTH){
        float aspectRatio = (float) (GAME_WIDTH/GAME_HEIGHT);
        this.projectionMatrix = new Matrix4f();
        projectionMatrix.identity();
        this.projectionMatrix.perspective(FOV, aspectRatio, z_near, z_Far);
    }
    public Matrix4f getProjectionMatrix(){
        return projectionMatrix;
    }
    public void calcIvProjection(int GAME_WIDTH, int GAME_HEIGHT){
        float aspectRatio = (float) (GAME_WIDTH/GAME_HEIGHT);
        this.IvProjectionMatrix = new Matrix4f();
        IvProjectionMatrix.identity();
        float z_near = 0.01f;
        float z_Far = 1000.0f;
        this.IvProjectionMatrix.perspective(FOV, aspectRatio, z_near, z_Far);
    }
    public Matrix4f setWorldMatrix(Vector3f offset, Vector3f rotation, float scale){
        Matrix4f WorldMatrix;
        WorldMatrix = new Matrix4f();
        WorldMatrix.identity().translate(offset).
                rotateX((float) Math.toRadians(rotation.x)).
                rotateY((float) Math.toRadians(rotation.y)).
                rotateZ((float) Math.toRadians(rotation.z)).
                scale(scale);
        return WorldMatrix;
    }
    public Matrix4f setWeaponsWorldMatrix(Vector3f offset, Vector3f rotation, float scale){
        Matrix4f WorldMatrix = new Matrix4f();
        WorldMatrix.identity().translate(offset).
                rotateX((float) Math.toRadians(rotation.x)).
                rotateY((float) Math.toRadians(rotation.y)).
                rotateZ((float) Math.toRadians(rotation.z));
        WorldMatrix.scale(scale);
        return WorldMatrix;
    }
    public Matrix4f calcViewMatrix(Camera camera){
        Vector3f Pos = camera.getPosition();
        Vector3f Ros = camera.getRotation();
        this.viewMatrix = new Matrix4f();
        this.viewMatrix.identity();
        this.viewMatrix.rotate((float) Math.toRadians(Ros.x), new Vector3f(1, 0, 0)).
                rotate((float) Math.toRadians(Ros.y), new Vector3f(0, 1, 0)).
                rotate((float) Math.toRadians(Ros.z), new Vector3f(0, 0, 1));
        this.viewMatrix.translate(Pos.x, Pos.y, Pos.z);
        return this.viewMatrix;
    }
    public Matrix4f getInviewMatrix(Camera camera){
        Matrix4f viewMatrix;
        Vector3f Pos = camera.getPLayerPos();
        Vector3f Ros = camera.getRotation();
        viewMatrix = new Matrix4f();
        viewMatrix.identity();
        viewMatrix.rotate((float) Math.toRadians(Ros.x), new Vector3f(1, 0, 0)).
                rotate((float) Math.toRadians(Ros.y), new Vector3f(0, 1, 0)).
                rotate((float) Math.toRadians(Ros.z), new Vector3f(0, 0, 1));
        viewMatrix.translate(Pos.x, Pos.y, Pos.z);
        return viewMatrix.invert();
    }
    public Matrix4f getIvProjectionMatrix(){
        return IvProjectionMatrix;
    }
    public Matrix4f getOrthoMatrix(float right, float left, float top, float bottom){
        Matrix4f orthoMatrix = new Matrix4f();
        orthoMatrix.identity();
        orthoMatrix.ortho(left, right, bottom, top, z_near, z_Far);
        return orthoMatrix;
    }
    public Matrix4f getModelViewMatrix(Camera camera, Vector3f offset, Vector3f rotation, float Scale){
        return calcViewMatrix(camera).mul(setWorldMatrix(offset, rotation, Scale));
    }
    public Matrix4f getParticlesModelViewMatrix(Camera camera, Vector3f offset, Vector3f rotation, float scale, Vector3f fall){
        return calcViewMatrix(camera).mul(setWorldMatrix(offset, rotation, scale)).translate(fall);
    }
    public Matrix4f getTranslation(Vector3f offset){
        Matrix4f TranslationMatrix = new Matrix4f();
        TranslationMatrix.identity();
        TranslationMatrix.translate(offset);
        return TranslationMatrix;
    }
    public void LightDirView(DirectionalLight directionalLight, Camera camera){
        Vector4f currLightDir = new Vector4f(directionalLight.getDirectionLight(), 0);
        currLightDir.mul(calcViewMatrix(camera));
        directionalLight.setDirLight(currLightDir.x, currLightDir.y, currLightDir.z);
    }
}
