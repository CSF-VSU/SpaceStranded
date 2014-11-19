package ru.vsu.csf.twopeoplestudios.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.vsu.csf.twopeoplestudios.model.characters.Hero;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.HerbStorage;

public class MapRenderer {

    Hero hero; //TODO: заменить на map, который содержит hero.
    HerbStorage herbStorage;

    TextureRegion heroTexture;

    public Hero getHero() {
        return hero;
    }

    public MapRenderer() {
        herbStorage = new HerbStorage();
        hero = new Hero();

        heroTexture = new TextureRegion(new Texture(Gdx.files.internal("gfx/characters/hero.png")));
    }

    public void render(Batch batch, float delta) {
        hero.update(delta);
        herbStorage.draw(batch, 0);
        batch.draw(heroTexture, hero.getPosition().x, hero.getPosition().y, 1, 2);
        hero.update(delta);
    }
}
