package Material;

import org.joml.Vector3f;

public class DirectionalLight {

    double LightAngle;
    Material material;
    public DirectionalLight(Material material){
        this.material = material;
        this.LightAngle = -90;
        this.material.setLightColor(0.6f, 0.6f, 0.6f);
    
    }
    public void ChangeDirection(){
        if(LightAngle > 90){
            LightAngle = -270;
        }
        LightAngle += 0.6f;
        float angleRad = (float) Math.toRadians(LightAngle);

        this.material.getLightSource().x = (float) Math.sin(angleRad) * 10;
        this.material.getLightSource().y = (float) Math.cos(angleRad) * 10;
    }
    public Vector3f getDirectionLight(){
        return this.material.getLightSource();
    }
    public void setDirLight(float x, float y, float z){
        this.material.setLightSource(x, y, z);
    }
    public void setLightColor(Vector3f LightColor, double LightAngle, float increase){
        if(LightAngle > -90 && LightAngle < 45){
            LightColor.x += increase;
            LightColor.y += increase;
            LightColor.z += increase;
        }
        if(LightAngle > -45 && LightAngle < 0){
            LightColor.x += increase;
            LightColor.y += increase;
            LightColor.z += increase;
        }
        if(LightAngle > 0 && LightAngle < 45){
            LightColor.x += increase;
            LightColor.y += increase;
            LightColor.z += increase;
        }
        if(LightAngle > 45 && LightAngle < 90){
            LightColor.x -= increase;
            LightColor.y -= increase;
            LightColor.z -= increase;
        }
        if(LightAngle < -90 && LightAngle > -180){
            LightColor.x -= increase;
            LightColor.y -= increase;
            LightColor.z -= increase;
        }
        if(LightAngle < -180 && LightAngle > -270){
            LightColor.x += increase;
            LightColor.y += increase;
            LightColor.z += increase;
        }
    }
}
