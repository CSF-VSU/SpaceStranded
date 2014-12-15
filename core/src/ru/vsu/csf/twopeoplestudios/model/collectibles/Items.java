package ru.vsu.csf.twopeoplestudios.model.collectibles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class Items {

    private static final int GROUP_SIZE = 99;

    private static final int HERB_ID = 0;
    private static final int ITEM_ID = 100;
    private static final int RANGED_WEAPON_ID = 200;
    private static final int MELEE_WEAPON_ID = 250;

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
            put(104, new Item(104, new TextureRegion(new Texture(Gdx.files.internal("gfx/items/4.png"))), "Cup", "This is an item", true));
            put(105, new Item(105, new TextureRegion(new Texture(Gdx.files.internal("gfx/items/5.png"))), "Scrap", "This is an item", true));
            put(106, new Item(106, new TextureRegion(new Texture(Gdx.files.internal("gfx/items/6.png"))), "Forcer", "This is an item", true));
            put(107, new Item(107, new TextureRegion(new Texture(Gdx.files.internal("gfx/items/7.png"))), "Gear", "This is an item", true));

            put(200, new Item(200, new TextureRegion(new Texture(Gdx.files.internal("gfx/items/8.png"))), "Blaster", "A blaster. Can shoot your brains out.", false));
            put(250, new Item(250, new TextureRegion(new Texture(Gdx.files.internal("gfx/items/9.png"))), "Knife", "A knife. Sharp as fuck", false));
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

    public String getItemName(int id) { return items.get(id).name; }

    public boolean checkIfCountable(int id) {
        return items.get(id).isCountable;
    }

    public boolean isHerb(int id) {
        return (id >= HERB_ID && id <= HERB_ID + GROUP_SIZE);
    }

    public boolean isMeleeWeapon(int id) {
        return  (id >= MELEE_WEAPON_ID && id <= MELEE_WEAPON_ID + GROUP_SIZE/2);
    }
}
