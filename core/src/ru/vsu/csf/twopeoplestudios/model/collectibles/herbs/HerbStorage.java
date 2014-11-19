package ru.vsu.csf.twopeoplestudios.model.collectibles.herbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class HerbStorage {

    ArrayList<HerbInfo> herbs;
    ArrayList<TextureRegion> textures;

    public HerbStorage() {
        herbs = new ArrayList<HerbInfo>();
        textures = new ArrayList<TextureRegion>();
        textures.add(0, new TextureRegion(new Texture(Gdx.files.internal("gfx/herbs/0.png"))));
    }

    public void draw(Batch batch, int herbId) {
        batch.draw(textures.get(herbId), 0, 0, 1, 1);
    }
}
