package ru.vsu.csf.twopeoplestudios.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import ru.vsu.csf.twopeoplestudios.renderers.MapRenderer;

public class GameScreen extends AbstractScreen {
    private MapRenderer renderer;
    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        renderer = new MapRenderer();


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
        renderer.render(batch, delta);
    }
}
