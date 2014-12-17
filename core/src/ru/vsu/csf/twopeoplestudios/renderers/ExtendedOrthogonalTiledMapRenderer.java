package ru.vsu.csf.twopeoplestudios.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import ru.vsu.csf.twopeoplestudios.model.characters.monsters.Monster;
import ru.vsu.csf.twopeoplestudios.model.collectibles.Items;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.Herb;
import ru.vsu.csf.twopeoplestudios.model.map.Map;
import ru.vsu.csf.twopeoplestudios.model.weapons.FlyingProjectile;
import ru.vsu.csf.twopeoplestudios.model.weapons.Weapons;

import java.util.ArrayList;
import java.util.List;

public class ExtendedOrthogonalTiledMapRenderer extends OrthogonalTiledMapRenderer {

    private TextureRegion heroTexture;
    private TextureRegion monsterTexture;

    private Map physMap;

    public ExtendedOrthogonalTiledMapRenderer(TiledMap map, Map physMap) {
        super(map);

        heroTexture = new TextureRegion(new Texture(Gdx.files.internal("gfx/characters/hero.png")));
        monsterTexture = new TextureRegion(new Texture(Gdx.files.internal("gfx/characters/monster.png")));

        this.physMap = physMap;
    }

    // @Override
    public void render(Batch batch) {
        int currentLayer = 0;

        for(MapLayer layer : map.getLayers()) {
            if (layer.isVisible())
                if (layer instanceof TiledMapTileLayer) {
                    if (currentLayer++ > 0)
                    {
                        batch.begin();

                        for (Herb h : physMap.herbs) {
                            batch.draw(Items.getInstance().getItemTexture(h.getId()),
                                    h.getPosition().x * MapRenderer.CELL_SIZE,
                                    h.getPosition().y * MapRenderer.CELL_SIZE);
                        }

                        for (Monster m : physMap.monsters) {
                            batch.draw(monsterTexture,
                                    m.getPosition().x * MapRenderer.CELL_SIZE - MapRenderer.CELL_SIZE,
                                    m.getPosition().y * MapRenderer.CELL_SIZE - MapRenderer.CELL_SIZE/2f);
                        }

                        for (FlyingProjectile f : physMap.projectiles) {
                            batch.draw(Weapons.getInstance().getTextureRegion(f.id),
                                    f.body.getPosition().x * MapRenderer.CELL_SIZE,
                                    f.body.getPosition().y * MapRenderer.CELL_SIZE);
                        }

                        batch.draw(heroTexture,
                                (physMap.hero.getHeroPosition().x * MapRenderer.CELL_SIZE - MapRenderer.CELL_SIZE),
                                physMap.hero.getHeroPosition().y * MapRenderer.CELL_SIZE - MapRenderer.CELL_SIZE/2f);

                        batch.end();
                    }
                    beginRender();
                    renderTileLayer((TiledMapTileLayer) layer);
                    endRender();
                }
        }
    }
}
