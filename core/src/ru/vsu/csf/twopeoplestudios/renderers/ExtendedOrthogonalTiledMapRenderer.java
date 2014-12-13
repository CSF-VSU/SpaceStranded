package ru.vsu.csf.twopeoplestudios.renderers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import ru.vsu.csf.twopeoplestudios.model.map.Map;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 09.12.2014.
 */
public class ExtendedOrthogonalTiledMapRenderer extends OrthogonalTiledMapRenderer {
    private TextureRegion heroTexture;
    private int drawSpritesAfterLayer = 1;
    private Map physMap;
    private OrthographicCamera camera;

    public ExtendedOrthogonalTiledMapRenderer(TiledMap map, Map _map, OrthographicCamera _camera, TextureRegion hero) {
        super(map);
        physMap = _map;
        camera = _camera;
        heroTexture = hero;

    //    regions = new ArrayList<TextureRegion>();
    }

//    public void addRegion(TextureRegion textureRegion){
//        regions.add(textureRegion);
//    }

    @Override
    public void render() {
     /*   beginRender();
        for(MapLayer layer : map.getLayers()) {
            if (layer.isVisible()) {

            }
        }
        int currentLayer = 0;
        for (MapLayer layer : map.getLayers()) {
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer)layer);
                    currentLayer++;
                    if(currentLayer == drawSpritesAfterLayer){
                        for(TextureRegion region : physMap.herbs)
                           // region.draw(this.getSpriteBatch());
                        currentLayer++;
                    }
                } else {
                    for (MapObject object : layer.getObjects()) {
                        renderObject(object);
                    }
                }
            }
        }*/
      //  endRender();
    }
}
