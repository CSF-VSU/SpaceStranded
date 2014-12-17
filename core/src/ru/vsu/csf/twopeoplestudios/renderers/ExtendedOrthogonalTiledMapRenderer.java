package ru.vsu.csf.twopeoplestudios.renderers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import ru.vsu.csf.twopeoplestudios.model.collectibles.Items;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.Herb;
import ru.vsu.csf.twopeoplestudios.model.map.Map;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 09.12.2014.
 */
public class ExtendedOrthogonalTiledMapRenderer extends OrthogonalTiledMapRenderer {
    private TextureRegion heroTexture;
  //  private int drawSpritesAfterLayer = 1;
    private Map physMap;
    private OrthographicCamera camera;
    private Vector3 lerpTarget = new Vector3();
    private Items items;

    private int currentLayer;

    public ExtendedOrthogonalTiledMapRenderer(TiledMap map, Map physMap, OrthographicCamera camera, TextureRegion heroTexture, Items items) {
        super(map);
        this.physMap = physMap;
        this.heroTexture = heroTexture;
        this.camera = camera;
        this.items = items;


    }



    // @Override
    public void render(Batch batch) {
        currentLayer = 0;


        for(MapLayer layer : map.getLayers()) {
            if (layer.isVisible())
                if (layer instanceof TiledMapTileLayer) {
                    if (currentLayer++ > 0)
                    {
                        batch.begin();
                        for (Herb h : physMap.herbs) {
                            batch.draw(items.getItemTexture(h.getId()),
                                    h.getPosition().x * 64,
                                    h.getPosition().y * 64,
                                    1.5f * 64,
                                    1.5f * 64);
                        }

                        batch.draw(heroTexture,
                                (physMap.hero.getHeroPosition().x - 0.5f) * 64,
                                (physMap.hero.getHeroPosition().y - 0.5f) * 64,
                                1.7f * 64,
                                (18 * 1.7f / 11f) * 64);
                        batch.end();
                    }
                    beginRender();
                    renderTileLayer((TiledMapTileLayer) layer);
                    endRender();
                }

        }







    }
}
