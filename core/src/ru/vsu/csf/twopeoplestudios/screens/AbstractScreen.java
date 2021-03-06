package ru.vsu.csf.twopeoplestudios.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.vsu.csf.twopeoplestudios.renderers.ui.UISpriteHolder;

public class AbstractScreen implements Screen {

    Game game;
    final SpriteBatch cameraBatch = new SpriteBatch();
    final SpriteBatch uiBatch = new SpriteBatch();

    public AbstractScreen(Game game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        UISpriteHolder.init();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        cameraBatch.dispose();
    }
}
