package ru.vsu.csf.twopeoplestudios.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import ru.vsu.csf.twopeoplestudios.model.world.MapTile;
import ru.vsu.csf.twopeoplestudios.renderers.MapRenderer;
import ru.vsu.csf.twopeoplestudios.renderers.UiRenderer;

public class GameScreen extends AbstractScreen {
    private MapRenderer renderer;
    private UiRenderer uiRenderer;


    //region backgroundDrawingVars
    private TiledMap tiledMap;
    private MapLayers layers;
    private TiledMapRenderer tiledMapRenderer;
    private MapTile[][] mapScheme;
    //endregion

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        //region backgroundDrawing

        mapScheme = ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().map;
        tiledMap = new TiledMap();
        int mapWidth = mapScheme.length;
        int mapHeight = mapScheme[0].length;
        TextureRegion water = new TextureRegion(new Texture(new FileHandle("gfx/tiles/water.png")));
        TextureRegion land = new TextureRegion(new Texture(new FileHandle("gfx/tiles/grass.png")));
        TextureRegion sand = new TextureRegion(new Texture(new FileHandle("gfx/tiles/sand.png")));
        TextureRegion tree = new TextureRegion(new Texture(new FileHandle("gfx/tiles/tree-icon.png")));


        TiledMapTile waterTile = new StaticTiledMapTile(water);
        TiledMapTile landTile = new StaticTiledMapTile(land);
        TiledMapTile sandTile = new StaticTiledMapTile(sand);
        TiledMapTile treeTile = new StaticTiledMapTile(tree);

        TiledMapTileLayer layer = new TiledMapTileLayer(128, 64, 64, 64);
        MapLayers layers = tiledMap.getLayers();

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

       // tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        //endregion


        renderer = new MapRenderer(tiledMap);
        renderer.map.hero.initGameScreen(this);

        uiRenderer = new UiRenderer(renderer.map.hero);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        //Gdx.gl.glClearColor(0.5f, 0.87f, 0.1f, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render(cameraBatch, delta);
        uiRenderer.render(uiBatch);
    }

    public void showInventory() {
        uiRenderer.setShowingInventory(true);
    }

    public void hideInventory() {
        uiRenderer.setShowingInventory(false);
    }

    public void panelCellSelect(int index) {
        renderer.map.hero.getPanel().selectItem(index);
    }
}
