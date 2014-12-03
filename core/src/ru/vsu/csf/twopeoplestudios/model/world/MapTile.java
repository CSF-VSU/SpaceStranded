package ru.vsu.csf.twopeoplestudios.model.world;

public class MapTile {

    public TerrainType type;
    public int height;

    public MapTile() {
        type = TerrainType.GROUND;
        height = 0;
    }
}
