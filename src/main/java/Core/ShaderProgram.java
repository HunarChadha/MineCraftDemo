package Core;

import Material.Material;
import Scene.Fog;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

    public final int ProgramID;
    private int vertexShader;
    private int FragmentShader;
    private final Map<String, Integer> uniforms;

    public ShaderProgram() throws Exception{
        ProgramID = glCreateProgram();
        uniforms = new HashMap<>();
        if(ProgramID == 0){
            throw new Exception("Error ProgramID is 0");
        }
    }
    public void createUniforms(String UniformName){
        int uniformLocation = glGetUniformLocation(ProgramID, UniformName);
        uniforms.put(UniformName, uniformLocation);
    }
    public void createPointLightUniform(String uniformName) {
        createUniforms(uniformName + ".colour");
        createUniforms(uniformName + ".position");
        createUniforms(uniformName + ".intensity");
        createUniforms(uniformName + ".att.constant");
        createUniforms(uniformName + ".att.linear");
        createUniforms(uniformName + ".att.exponent");
    }
    public void createDiffuseUniform(String uniformName){
        createUniforms(uniformName + ".color");
        createUniforms(uniformName + ".intensity");
    }

    public void createMaterialUniform(String uniformName)  {
        createUniforms(uniformName + ".ambient");
        createUniforms(uniformName + ".diffuse");
        createUniforms(uniformName + ".specular");
        createUniforms(uniformName + ".hasTexture");
        createUniforms(uniformName + ".reflectance");
    }

    public void setFogUniforms(String name, Fog fog){
        setUniforms(name + ".activen", fog.getActive() ? 1:0);
        setUniforms(name + ".density", fog.getDensity());
        setUniforms(name + ".fogColor", fog.getFogColor());
    }

    public void setUniforms(String UniformName, Vector4f value){
        glUniform4f(uniforms.get(UniformName), value.x, value.y, value.z, value.w);
    }
    public void setUniforms(String UniformName, Matrix4f[] Matrix){
        try(MemoryStack stack = MemoryStack.stackPush()){
            int length = Matrix != null ? Matrix.length: 0;
            FloatBuffer fb = stack.mallocFloat(16* length);
            for(int i = 0; i<length; i++){
                Matrix[i].get(16*i, fb);
            }
            glUniformMatrix4fv(uniforms.get(UniformName), false, fb);
        }
    }
    public void setDiffuseUniforms(String UniformName, Material material){
        setUniforms(UniformName + ".LightSource", material.getLightSource());
        setUniforms(UniformName + ".LightColor", material.getDiffuseLightColor());
        setUniforms(UniformName + ".intensity", material.getIntensity());
    }
    public void createDiffuseUniforms(String uniformName){
        createUniforms(uniformName + ".LightSource");
        createUniforms(uniformName + ".LightColor");
        createUniforms(uniformName + ".intensity");
    }
    public void createFogUniforms(String uniformName){
        createUniforms(uniformName + ".activen");
        createUniforms(uniformName + ".fogColor");
        createUniforms(uniformName + ".density");
    }

    public void setUniforms(String UniformName, Matrix4f value){
        try(MemoryStack stack = MemoryStack.stackPush()){
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            glUniformMatrix4fv(uniforms.get(UniformName), false, fb);
        }
    }
    public void setUniforms(String UniformName, float value){
        glUniform1f(uniforms.get(UniformName), value);
    }
    public void setUniforms(String UniformName, Vector3f value){
        glUniform3f(uniforms.get(UniformName), value.x, value.y, value.z);
    }
    public void setUniforms(String UniFormNAME, int value){
        glUniform1i(uniforms.get(UniFormNAME), value);
    }
    public void createFragShader(String ShaderType) throws Exception{
        FragmentShader = createShader(ShaderType, GL_FRAGMENT_SHADER);
    }
    public void createVertexShader(String shaderType)throws Exception{
        vertexShader = createShader(shaderType, GL_VERTEX_SHADER);
    }
    public void createDirectionalLightUniform(String uniformName){
        createUniforms(uniformName + ".colour");
        createUniforms(uniformName + ".direction");
        createUniforms(uniformName + ".intensity");
    }
    public void setUniform(String uniformName, Matrix4f[] matrices) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            int length = matrices != null ? matrices.length : 0;
            FloatBuffer fb = stack.mallocFloat(16 * length);
            for (int i = 0; i < length; i++) {
                matrices[i].get(16 * i, fb);
            }
            glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
        }
    }
    public void createHasPBRuniform(String Name){
        createUniforms(Name + ".HasNormal");
        createUniforms(Name + ".HasRoughness");
        createUniforms(Name + ".HasMetallic");
        createUniforms(Name + ".HasARMMap");
        createUniforms(Name + ".HasAo");
    }
    public void createPBRMatUniform(String Name){
        createUniforms(Name + ".NormalMap");
        createUniforms(Name + ".RoughnessMap");
        createUniforms(Name + ".MetallicMap");
        createUniforms(Name + ".AOMap");
        createUniforms(Name + ".DiffuseMap");
        createUniforms(Name + ".ARMMap");
    }
    public void setPbrMat(String Name){
        setUniforms(Name + ".DiffuseMap", 0);
        setUniforms(Name + ".NormalMap", 1);
        setUniforms(Name + ".AOMap", 2);
        setUniforms(Name + ".ARMMap", 3);
        setUniforms(Name + ".MetallicMap", 4);
        setUniforms(Name + ".RoughnessMap", 5);
    }
    // ...

    public int createShader(String shaderType, int shaderCode)throws Exception{
        int shaderID = glCreateShader(shaderCode);
        if(shaderID == 0){
            throw new Exception("Cannot create shader");
        }
        glShaderSource(shaderID, shaderType);
        glCompileShader(shaderID);
        if(glGetShaderi(shaderID, GL_COMPILE_STATUS)==0){
            throw new RuntimeException("Error Compile shader" + glGetShaderInfoLog(shaderID));
        }
        glAttachShader(ProgramID, shaderID);
        return shaderID;
    }
    public void link() throws Exception{
        glLinkProgram(ProgramID);
        if (glGetProgrami(ProgramID, GL_LINK_STATUS) == 0){
            throw new Exception("Cannot link" + glGetProgramInfoLog(ProgramID, 1024));
        }
        if(vertexShader != 0){
            glDetachShader(ProgramID, vertexShader);
        }
        if(FragmentShader != 0){
            glDetachShader(ProgramID, FragmentShader);
        }
        glValidateProgram(ProgramID);
        if(glGetProgrami(ProgramID, GL_VALIDATE_STATUS) == 0){
            throw new Exception("Warning validate" + glGetProgramInfoLog(ProgramID, 1024));
        }
    }
    public void bind(){
        glUseProgram(ProgramID);
    }
    public void unbind(){
        glUseProgram(0);
    }
    public void print(){
        System.out.println("Hey");
    }
    public void cleanUp(){
        unbind();
        if(ProgramID != 0){
            glDeleteProgram(ProgramID);
        }
    }
}