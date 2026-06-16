#version 330

layout(location = 0) in vec3 Position;
layout(location = 1) in vec2 inTexCoords;

out vec2 outTexCoords;

uniform mat4 WorldMatrix;
uniform mat4 ProjectionMatrix;
uniform mat4 viewMatrix;
void main() {
    gl_Position = ProjectionMatrix * viewMatrix *WorldMatrix * vec4(Position, 1.0);
    outTexCoords = inTexCoords;
}
