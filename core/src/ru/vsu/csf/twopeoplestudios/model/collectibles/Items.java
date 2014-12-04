package ru.vsu.csf.twopeoplestudios.model.collectibles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class Items {

    private static Items instance;

    private HashMap<Integer, Item> items;

    private Items() {
        items = new HashMap<Integer, Item>(104) {{
            put(0, new Item(0, new TextureRegion(new Texture(Gdx.files.internal("gfx/herbs/0.png"))), "Grass", "This is a herb", true));
            put(1, new Item(1, new TextureRegion(new Texture(Gdx.files.internal("gfx/herbs/1.png"))), "Mushroom", "This is a herb", true));
            put(2, new Item(2, new TextureRegion(new Texture(Gdx.files.internal("gfx/herbs/2.png"))), "Log", "This is a herb", true));

            put(100, new Item(100, new TextureRegion(new Texture(Gdx.files.internal("gfx/items/0.png"))), "Battery", "This is an item", true));
            put(101, new Item(101, new TextureRegion(new Texture(Gdx.files.internal("gfx/items/1.png"))), "Scanner", "This is an item", true));
            put(102, new Item(102, new TextureRegion(new Texture(Gdx.files.internal("gfx/items/2.png"))), "Ore", "This is an item", true));
            put(103, new Item(103, new TextureRegion(new Texture(Gdx.files.internal("gfx/items/3.png"))), "Metal", "This is an item", true));
        }};
    }

    public static Items getInstance() {
        if (instance == null)
            instance = new Items();
        return instance;
    }

    public TextureRegion getItemTexture(int id) {
        return items.get(id).texture;
    }

    public boolean checkIfCountable(int id) {
        return items.get(id).isCountable;
    }
}
