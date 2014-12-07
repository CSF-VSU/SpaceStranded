package ru.vsu.csf.twopeoplestudios.model.world;

public class MapEdge {

    public boolean isRiver;
    public int direction;//1 - right/up, -1 - left/down, 0 - unknown

    public MapEdge() {
        isRiver = false;
        direction = 0;
    }
}
