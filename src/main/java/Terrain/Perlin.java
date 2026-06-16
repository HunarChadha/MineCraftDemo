package Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Perlin {

    Random random;
    float[]Elevation;
    List<Float> NumberOfBlocks = new ArrayList<>();

    public Perlin(int Height){
        init(Height);
    }
    public void CalcNoise(int nOctaves, float[] Elevation, int MaxHeight,int Width, float[] FinalHeight, float ScalingFactor){
        for(int i = 0; i<MaxHeight; i++) {
            for(int y = 0; y<Width; y++){
                float fNoise = 0.0f;
                float Scale = 1.0f;
                float fScaleAcc = 0.0f;
                for(int o = 0; o<nOctaves; o++){
                    int Octave = Width / 2;
                    int sampleX1 = (i / Octave) * Octave;
                    int sampleY1 = (y/Octave) * Octave;

                    int sampleX2 = (sampleX1 + Octave) % Width;
                    int sampleY2 = (sampleY1 + Octave) % MaxHeight;

                    float BlendX = (float) (i - sampleX1) / (float) Octave;
                    float BlendY = (float) (y - sampleY1) / (float) Octave;

                    float fSampleT = (1.0f - BlendX) * Elevation[sampleY1 * Width + sampleX1] + BlendX * Elevation[sampleY1 * Width + sampleX2];
                    float fSampleB = (1.0f - BlendX) * Elevation[sampleY2 * Width + sampleX1] + BlendX * Elevation[sampleY2 * Width + sampleX2];

                    fNoise += (BlendY * (fSampleB - fSampleT) + fSampleT) * Scale;
                    fScaleAcc += Scale;
                    Scale = Scale / ScalingFactor;
                }
                FinalHeight[y * Width + i] = fNoise / fScaleAcc;
            }

        }
    }
    public void init(int height){
        random = new Random();
        int MaxLength = 20;
        Elevation = new float[MaxLength * MaxLength];
        for(int i = 0; i<MaxLength * MaxLength; i++){
            float Height = GenerateRandom();
            Elevation[i] = Height;
        }
        float[] FinalHeight = new float[MaxLength * MaxLength];
        CalcNoise(2, Elevation, MaxLength, MaxLength,FinalHeight, 2.5f);

        for (float v : FinalHeight) {
            float Height = v;
            Height = (Height * height);
            NumberOfBlocks.add(Height);
        }

    }
    public List<Float> getElevation(){
        return NumberOfBlocks;
    }
    public float GenerateRandom(){
        float MaxY = 1.00f;
        float MinY = 0f;
        return random.nextFloat() * (MaxY - MinY) + MinY;
    }
    public float getRandomHeight(){
        float Max = 15;
        float min = 10;
        return random.nextFloat() *(Max - min) + min;
    }
}
