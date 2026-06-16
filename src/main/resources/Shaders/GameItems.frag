# version 330

out vec4 Color;

in vec2 oTexCoords;
uniform sampler2D texture_sampler;

void main(){
    Color = texture(texture_sampler, oTexCoords);
}