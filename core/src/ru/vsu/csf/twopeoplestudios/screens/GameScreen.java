package ru.vsu.csf.twopeoplestudios.screens;

import com.badlogic.gdx.Game;
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
        uiRenderer = new UiRenderer(renderer.map.hero);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        renderer.render(cameraBatch, delta);
        uiRenderer.render(uiBatch, delta);
    }
}
