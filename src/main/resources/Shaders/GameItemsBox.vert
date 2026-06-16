# version 330

layout(location = 0) in vec3 Position;
layout(location = 1) in vec2 iTexCoords;

out vec2 oTexCoords;
uniform mat4 ProjectionMatrix;
uniform mat4 ModelMatrix;
void main(){
    gl_Position = ProjectionMatrix* ModelMatrix * vec4(Position, 1);
    oTexCoords = iTexCoords;
}