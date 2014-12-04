package ru.vsu.csf.twopeoplestudios.model.collectibles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Item {

    int id;
    TextureRegion texture;
    String name;
    String description;
    boolean isCountable;

    public Item(int id, TextureRegion texture, String name, String description, boolean isCountable) {
        this.id = id;
        this.texture = texture;
        this.name = name;
        this.description = description;
        this.isCountable = isCountable;
    }
}
