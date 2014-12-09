package ru.vsu.csf.twopeoplestudios.model.world;

public class MapEdge {

    public boolean isRiver;
    public int direction;//1 - вправо/вверх, -1 - влево/вниз, 0 - неизвестно

    public MapEdge() {
        isRiver = false;
        direction = 0;
    }
}
