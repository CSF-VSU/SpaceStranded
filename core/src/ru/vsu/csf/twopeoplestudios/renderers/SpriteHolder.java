package ru.vsu.csf.twopeoplestudios.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class SpriteHolder {
    public static TextureRegion inventoryCell;
    public static ArrayList<TextureRegion> textures;

    private SpriteHolder() {
    }

    public static void init() {
        inventoryCell = new TextureRegion(new Texture(Gdx.files.internal("gfx/tiles/water.png")));
        textures = new ArrayList<TextureRegion>();
        textures.add(0, new TextureRegion(new Texture(Gdx.files.internal("gfx/herbs/0.png"))));
        textures.add(1, new TextureRegion(new Texture(Gdx.files.internal("gfx/herbs/1.png"))));
        textures.add(2, new TextureRegion(new Texture(Gdx.files.internal("gfx/herbs/2.png"))));
    }

    public static TextureRegion getTexture(int id) {
        return textures.get(id);
    }
}
