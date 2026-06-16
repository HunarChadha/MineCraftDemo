#version 330

struct DiffuseLight{
    vec3 LightSource;
    vec3 LightColor;
    float intensity;
};

struct Fog{
    int activen;
    vec3 fogColor;
    float density;
};

out vec4 FragColor;
in vec2 outtexCoords;
in vec3 mvVertexNormals;
in vec3 mvVertexPos;
uniform sampler2D texture_sampler;
uniform int Selected;
uniform vec3 AmbientLight;
uniform DiffuseLight DiffuseComp;
uniform Fog fog;

vec4 calcDiffusecomSpecular(vec3 mvPosition, DiffuseLight Lighting, vec3 nor, vec4 TextureColor){
    vec4 DiffuseColor = vec4(0.0, 0.0, 0.0, 0.0);
    // Diffuse
    vec3 lightdir = Lighting.LightSource - mvPosition;
    vec3 to_lightSoure = normalize(lightdir);
    float DiffuseFactor = max(dot(nor, to_lightSoure), 0.0);

    DiffuseColor = TextureColor * vec4(Lighting.LightColor, 1.0) * Lighting.intensity * DiffuseFactor;
    return DiffuseColor;
}
vec4 calcFog(vec3 vertexPos, vec4 TextureColor, vec3 ambientLight, DiffuseLight drrLight, Fog fog){

    vec3 fogColor = fog.fogColor * (ambientLight + drrLight.LightColor * drrLight.intensity);
    float Distance = length(vertexPos);

    float fogFactor = 1.0 / exp( (Distance * fog.density) * (Distance * fog.density));
    fogFactor = clamp(fogFactor, 0.0, 1.0);

    vec3 result = mix(fogColor, TextureColor.xyz, fogFactor);
    return vec4(result.xyz, TextureColor.w);
}

void main(){
    vec4 AmbinetC;
    vec4 DiffuseC;
    vec4 TextureColor;
    vec4 FinalTexture;

    TextureColor = texture(texture_sampler, outtexCoords);
    AmbinetC = vec4(AmbientLight, 1.0) * TextureColor;
    vec3 am = vec3(AmbinetC.x, AmbinetC.y, AmbinetC.z);

    if(fog.activen == 1){
        FinalTexture = calcFog(mvVertexPos, TextureColor, am, DiffuseComp, fog);
    }
    else{
        DiffuseC = calcDiffusecomSpecular(mvVertexPos, DiffuseComp, mvVertexNormals, TextureColor);
        FinalTexture = DiffuseC + AmbinetC;
    }
    if(Selected == 1){

        FragColor = vec4(FinalTexture.x, FinalTexture.y, 1, FinalTexture.w);
    }
    else{
        FragColor = FinalTexture;
    }
}