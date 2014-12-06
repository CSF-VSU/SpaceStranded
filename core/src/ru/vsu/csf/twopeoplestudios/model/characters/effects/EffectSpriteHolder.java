package ru.vsu.csf.twopeoplestudios.model.characters.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.HerbProperties;

import java.util.HashMap;

public class EffectSpriteHolder {

    private HashMap<HerbProperties, TextureRegion> textures;

    private EffectSpriteHolder() {
        textures = new HashMap<HerbProperties, TextureRegion>() {{
            put(HerbProperties.HP_UP, new TextureRegion(new Texture(Gdx.files.internal("gfx/effects/hp_up.png"))));
            put(HerbProperties.HP_DOWN, new TextureRegion(new Texture(Gdx.files.internal("gfx/effects/hp_down.png"))));
            put(HerbProperties.FL_UP, new TextureRegion(new Texture(Gdx.files.internal("gfx/effects/fl_up.png"))));
            put(HerbProperties.FL_DOWN, new TextureRegion(new Texture(Gdx.files.internal("gfx/effects/fl_down.png"))));
            put(HerbProperties.ST_UP, new TextureRegion(new Texture(Gdx.files.internal("gfx/effects/st_up.png"))));
            put(HerbProperties.ST_DOWN, new TextureRegion(new Texture(Gdx.files.internal("gfx/effects/st_down.png"))));
        }};
    }

    private static EffectSpriteHolder instance;

    public static EffectSpriteHolder getInstance() {
        if (instance == null)
            instance = new EffectSpriteHolder();
        return instance;
    }

    public TextureRegion getTexture(HerbProperties effect) {
        return textures.get(effect);
    }
}
