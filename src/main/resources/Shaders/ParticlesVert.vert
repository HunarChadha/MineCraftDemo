#version 330

layout(location = 0) in vec3 Position;
layout(location = 2) in vec3 inColors;

out vec3 outColors;

uniform mat4 ProjectionMatrix;
uniform mat4 ModelViewMatrix;

void main() {
    gl_Position = ProjectionMatrix * ModelViewMatrix * vec4(Position, 1.0);
    outColors = inColors;
}
