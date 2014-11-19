package ru.vsu.csf.twopeoplestudios.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import ru.vsu.csf.twopeoplestudios.renderers.MapRenderer;

public class GameScreen extends AbstractScreen {


    private MapRenderer renderer;
    private OrthographicCamera camera;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        renderer = new MapRenderer();
        camera = new OrthographicCamera(40, 32);
        camera.position.set(0, 0, 0);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                renderer.getHero().keyDown(keycode);
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                renderer.getHero().keyUp(keycode);
                return true;
            }

            @Override
            public boolean keyTyped(char character) {
                renderer.getHero().keyTyped(character);
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        renderer.render(batch, delta);
        batch.end();
    }
}
