package ru.vsu.csf.twopeoplestudios.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import ru.vsu.csf.twopeoplestudios.model.world.MapTile;
import ru.vsu.csf.twopeoplestudios.model.world.TerrainType;
import ru.vsu.csf.twopeoplestudios.model.world.World;

import java.util.Random;

/**
 * Created by Alexander on 29.11.2014.
 */
public class TileDemoScreen extends AbstractScreen {

    private TiledMap map;
    private MapLayers layers;
    private TiledMapRenderer renderer;
    private OrthographicCamera camera;
    private MapTile[][] mapScheme;

    public TileDemoScreen(Game game) {
        super(game);
    }


    @Override
    public void show() {
        super.show();
        final int speed = 100;
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean keyTyped(char character) {
                if (character == 'w')
                    camera.position.set(camera.position.x, camera.position.y + speed, camera.position.z);
                if (character == 'a')
                    camera.position.set(camera.position.x - speed, camera.position.y, camera.position.z);
                if (character == 's')
                    camera.position.set(camera.position.x, camera.position.y - speed, camera.position.z);
                if (character == 'd')
                    camera.position.set(camera.position.x + speed, camera.position.y, camera.position.z);

                if (character == 'f')
                    camera.position.set(40000, 20000, camera.position.z);
                return super.keyTyped(character);
            }
        });
        mapScheme = World.getInstance().map;
        map = new TiledMap();
        int mapWidth = mapScheme.length;
        int mapHeight = mapScheme[0].length;
        TextureRegion water = new TextureRegion(new Texture(new FileHandle("gfx/tiles/water.png")));
        TextureRegion land = new TextureRegion(new Texture(new FileHandle("gfx/tiles/land4.png")));
        TextureRegion sand = new TextureRegion(new Texture(new FileHandle("gfx/tiles/beachbitch.png")));

        TiledMapTile waterTile = new StaticTiledMapTile(water);
        TiledMapTile landTile = new StaticTiledMapTile(land);
        TiledMapTile sandTile = new StaticTiledMapTile(sand);

        TiledMapTileLayer layer = new TiledMapTileLayer(4096, 2048, 20, 19);
        MapLayers layers = map.getLayers();





        for (int i = 0; i < mapWidth; i++)
            for (int j = 0; j < mapHeight; j++)
            {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                switch (mapScheme[i][j].type) {
                    case GROUND:
                        cell.setTile(landTile);
                        break;
                    case WATER:
                        cell.setTile(waterTile);
                        break;
                    case SAND:
                        cell.setTile(sandTile);
                        break;
                }
                layer.setCell(i, j, cell);
            }
        layers.add(layer);



        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(map);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();
    }

    @Override
    public void dispose() {
        //dispose objects
        super.dispose();
    }
}
