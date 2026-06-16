# Voxel Engine (Java + LWJGL)

A 3D voxel/sandbox engine built from scratch in Java, inspired by Minecraft. Procedurally generated terrain, a first-person camera, block placing/breaking, an inventory and crafting system, and a custom-built renderer on top of raw OpenGL.

This is a personal/learning project — the renderer, terrain generator, and physics are all hand-written rather than using an existing game engine.

## Features

- **Procedural terrain** generated with a custom multi-octave Perlin-noise implementation
- **First-person camera** with mouse-look and WASD movement
- **Block interaction** — place and break blocks in the world
- **Inventory & crafting** system with storing items
- **Weapons/tools** (wooden, golden, and diamond pickaxes) with their own models and textures
- **Lighting** via a directional light and material system
- **Atmospheric effects** — skybox, fog, and a particle-based rain system
- **Frustum culling** so off-screen geometry isn't rendered, plus AABB/ray intersection for block selection
- **Terrain collision** so the player doesn't fall through the world

## Tech stack

- **Java**
- **LWJGL 3** — OpenGL/GLFW bindings
- **JOML** — vector/matrix math
- **GLSL** — custom vertex/fragment shaders for terrain, weapons, particles, skybox, and UI items
- **Gradle** — build tool

## Project structure

```
main/
├── java/
│   ├── Core/          # Camera, mesh, shader program, texture, transformations, frustum culling
│   ├── GamePhysics/    # Collision detection and general physics
│   ├── Items/          # Inventory, items, and crafting recipes
│   ├── Material/       # Materials and directional lighting
│   ├── Model/           # Game screen / model data
│   ├── Player/          # Player-related state
│   ├── RenderManger/    # Render pipeline, shader/texture/mesh setup, the game engine itself
│   ├── Scene/            # Fog, skybox, rain/particles
│   ├── Terrain/           # Terrain generation, Perlin noise, biomes
│   ├── Weapons/            # Pickaxes
│   └── org/example/        # Main entry point and window setup
└── resources/
    ├── Models/      # .obj/.mtl models for blocks and weapons
    ├── Recipe/      # Crafting recipe data
    ├── Shaders/     # GLSL vertex/fragment shaders
    └── Textures/    # Texture atlases and images
```

## Prerequisites

- JDK 17 or later
- Gradle (or use the included Gradle wrapper, if present)
- A GPU/driver with OpenGL support

## ⚠️ Before you run it

The lwjgl may give you a warning but it will not crash the program

## Setup & run

1. Clone or download the project.
3. Build and run with Gradle:
   ```
   ./gradlew build
   ./gradlew run
   ```
   Or open the project in IntelliJ IDEA / your IDE of choice, let Gradle sync, and run `org.example.Main`.

The game launches in fullscreen by default with the mouse cursor captured for camera look.

## Controls

| Key / Input | Action |
|---|---|
| `W` `A` `S` `D` | Move forward / left / back / right |
| `Q` / `E` | Move down / up |
| Mouse | Look around |
| Left Click | Break / interact with a block |
| `F` | Open / close inventory |
| `0`–`9` | Select inventory slot |
| `B` | Craft selected item (while inventory is open) |
| `Space` | Jump |
| `Enter` | Confirm / start |

## Known issues & roadmap

- No build/run automation for the natives across OSes yet
- Limited error handling for missing assets
- Planned: more block types, saving/loading the world, better lighting, more tools and recipe
- Some may experience FPS drop if snow particles is rendered
