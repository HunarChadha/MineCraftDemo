#version 330

layout(location = 0) in vec3 Position;
layout(location = 1) in vec2 texCoords;
layout(location = 3) in vec3 VertexNormals;
out vec3 mvVertexPos;
out vec3 mvVertexNormals;
out vec2 outtexCoords;

uniform mat4 projection;
uniform mat4 modelViewMatrix;

void main(){
    vec4 mvPos = modelViewMatrix * vec4(Position, 1.0);
    gl_Position = projection * mvPos;
    mvVertexNormals = (modelViewMatrix * vec4(VertexNormals, 0.0)).xyz;
    mvVertexPos = mvPos.xyz;
    outtexCoords = texCoords;
}