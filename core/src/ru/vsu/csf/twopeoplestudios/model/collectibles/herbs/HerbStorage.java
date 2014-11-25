package ru.vsu.csf.twopeoplestudios.model.collectibles.herbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class HerbStorage {

    ArrayList<HerbInfo> herbs;
    public ArrayList<TextureRegion> textures;

    private HerbStorage() {
        herbs = new ArrayList<HerbInfo>();
        textures = new ArrayList<TextureRegion>();
        textures.add(0, new TextureRegion(new Texture(Gdx.files.internal("gfx/herbs/0.png"))));
    }

    private static HerbStorage instance;

    public static HerbStorage getInstance() {
        if (instance == null)
            instance = new HerbStorage();
        return instance;
    }

    public TextureRegion getTexture(int id) {
        return textures.get(id);
    }
}