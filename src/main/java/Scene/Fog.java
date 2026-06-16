package Scene;

import org.joml.Vector3f;

public class Fog {
    Vector3f fogColor;
    boolean active;
    float density;

    public Fog(Vector3f fogColor, boolean active, float density){
        this.fogColor = fogColor;
        this.active = active;
        this.density = density;
    }
    public boolean getActive(){
        return this.active;

    }
    public Vector3f getFogColor(){
        return this.fogColor;
    }
    public float getDensity(){
        return this.density;
    }
}
