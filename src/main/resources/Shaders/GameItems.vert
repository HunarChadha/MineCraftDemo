# version 330

layout(location = 0) in vec3 Position;
layout(location = 1) in vec2 iTexCoords;

out vec2 oTexCoords;
uniform mat4 projectionMatrix;
uniform mat4 ModelMatrix;
uniform mat4 viewMatrix;
uniform int InventoryOpen;

void main(){
    if(InventoryOpen == 1){
        gl_Position = projectionMatrix * viewMatrix * ModelMatrix * vec4(Position, 1);
    }
    if(InventoryOpen == 0){
        gl_Position = projectionMatrix * ModelMatrix *vec4(Position, 1);
    }
    oTexCoords = iTexCoords;
}