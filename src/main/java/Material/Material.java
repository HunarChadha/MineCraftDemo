package Material;

import org.joml.Vector3f;
public class Material {
    Vector3f ambientLight;
    Vector3f LightSource;
    float intensity;
    Vector3f DiffuseLightColor;
    public Material(){
        ambientLight = new Vector3f(0.4f, 0.4f, 0.4f);
        LightSource = new Vector3f(0, 0, 0);
        intensity = 0.5f;
        DiffuseLightColor = new Vector3f(0, 0, 1);
    }
    public Vector3f getAmbinetLight(){
        return ambientLight;
    }
    public Vector3f getLightSource(){
        return this.LightSource;
    }
    public Vector3f getDiffuseLightColor(){
        return DiffuseLightColor;
    }
    public float getIntensity(){
        return intensity;
    }
    public void setLightSource(float x, float y, float z){
        this.LightSource.set(x, y, z);
    }
    public void setLightColor(float x, float y, float z){
        DiffuseLightColor.set(x, y, z);
    }
}
