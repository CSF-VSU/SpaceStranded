package ru.vsu.csf.twopeoplestudios.model.collectibles.herbs;

import com.badlogic.gdx.math.Vector2;
import ru.vsu.csf.twopeoplestudios.model.collectibles.ICollectible;

public class Herb implements ICollectible {
    protected int id;
    protected Vector2 position;

    public Herb(int id, Vector2 position) {
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
