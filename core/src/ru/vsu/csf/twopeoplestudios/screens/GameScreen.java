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

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();



        renderer = new MapRenderer();
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
