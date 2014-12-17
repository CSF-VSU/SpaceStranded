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
import ru.vsu.csf.twopeoplestudios.screens.stages.GameStage;

public class GameScreen extends AbstractScreen {
    private MapRenderer renderer;
    private UiRenderer uiRenderer;
    private GameStage stage;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();



        renderer = new MapRenderer();
        stage = new GameStage();
        uiRenderer = new UiRenderer(renderer.map.hero, stage);
        stage.init(renderer.map.hero, uiRenderer, renderer);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        renderer.render(cameraBatch, delta);
        uiRenderer.render(uiBatch, delta);
    }
}
