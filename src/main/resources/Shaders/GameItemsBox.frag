# version 330

out vec4 Color;
in vec2 oTexCoords;
uniform sampler2D texture_sampler;
uniform int selected;

void main(){
    vec4 Texture = texture(texture_sampler, oTexCoords);
    if(selected == 1){
        Color = vec4(Texture.x, Texture.y, 1, 1);
    }
    else{
        Color = Texture;
    }
}