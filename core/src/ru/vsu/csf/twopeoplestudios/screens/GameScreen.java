package ru.vsu.csf.twopeoplestudios.screens;

import com.badlogic.gdx.Game;
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
