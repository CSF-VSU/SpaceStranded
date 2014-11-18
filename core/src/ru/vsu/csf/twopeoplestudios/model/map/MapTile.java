package ru.vsu.csf.twopeoplestudios.model.map;

public class MapTile {

    public TerrainType type;
    public int herbId;
    public int height;

    public MapTile() {
        type = TerrainType.GROUND;
        herbId = -1;
        height = 0;
    }
}
