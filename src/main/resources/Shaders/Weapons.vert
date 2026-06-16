#version 330

layout(location = 0) in vec3 Position;
layout(location = 1) in vec2 inTexCoords;

out vec2 outTexCoords;
uniform mat4 WeaponsWorldMatrix;
uniform mat4 WeaponsProjectionMatrix;
uniform mat4 WeaponsViewMatrix;
void main() {
    vec4 WorldPos = WeaponsWorldMatrix * vec4(Position, 1.0);
    gl_Position = WeaponsProjectionMatrix * WeaponsViewMatrix * WorldPos;
    outTexCoords = inTexCoords;
}
