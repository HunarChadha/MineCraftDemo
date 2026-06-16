# version 330

out vec4 gl_FragColor;
in vec3 outColors;

void main() {
    gl_FragColor = vec4(outColors, 1.0);
}
