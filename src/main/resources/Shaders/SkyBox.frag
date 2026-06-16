#version 330

struct Fog{
    int activen;
    vec3 fogColor;
    float density;
};

out vec4 gl_FragColor;
in vec2 outTexCoords;

uniform float FogFactor;
uniform sampler2D Texture;
uniform Fog fog;

vec4 calcFog(vec4 TextureColor, Fog fog, float fogFactor){
    vec3 fogColor = fog.fogColor;
    vec3 result = mix(fogColor, TextureColor.xyz, fogFactor);
    return vec4(result.xyz, TextureColor.w);
}

void main() {
    vec4 TextureColor = texture(Texture, outTexCoords);
    if(fog.activen == 1){
        gl_FragColor = calcFog(TextureColor, fog, FogFactor);
    }
    else{
        gl_FragColor = TextureColor;
    }
}
