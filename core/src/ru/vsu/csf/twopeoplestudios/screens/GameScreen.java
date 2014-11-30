package ru.vsu.csf.twopeoplestudios.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
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

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                renderer.map.hero.keyDown(keycode);
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                renderer.map.hero.keyUp(keycode);
                return true;
            }

            @Override
            public boolean keyTyped(char character) {
                renderer.map.hero.keyTyped(character);
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.5f, 0.7f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render(batch, delta);
        uiRenderer.render(batch, renderer.camera);
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
