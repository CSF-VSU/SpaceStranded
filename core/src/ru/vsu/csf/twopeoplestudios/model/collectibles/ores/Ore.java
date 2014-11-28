package ru.vsu.csf.twopeoplestudios.model.collectibles.ores;

import com.badlogic.gdx.math.Vector2;

public class Ore {

    protected int id;
    protected Vector2 position;

    public Ore(int id, Vector2 position) {
        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public Vector2 getPosition() {
        return position;
    }
}
