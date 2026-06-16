#version 330

out vec4 Color;
in vec2 outTexCoords;
uniform sampler2D Texture;

void main() {
    Color = texture(Texture, outTexCoords);
}
