package ru.vsu.csf.twopeoplestudios.renderers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import ru.vsu.csf.twopeoplestudios.Values;
import ru.vsu.csf.twopeoplestudios.model.characters.Hero;
import ru.vsu.csf.twopeoplestudios.model.collectibles.Collectible;
import ru.vsu.csf.twopeoplestudios.renderers.ui.UISpriteHolder;
import ru.vsu.csf.twopeoplestudios.screens.stages.GameStage;

public class UiRenderer {

    private static float INVENTORY_MARGIN_LEFT = 200;
    private static float INVENTORY_MARGIN_TOP = 800;
    private static float PANEL_MARGIN_LEFT = 400;
    private static float PANEL_MARGIN_TOP = 1020;

    private static int INVENTORY_SIZE_WIDTH = 10;
    private static int INVENTORY_SIZE_HEIGHT = 4;
    private static int PANEL_SIZE = 10;

    public Hero hero;

    private GameStage stage;

    private boolean isShowingInventory;

    public UiRenderer(Hero hero) {
        this.hero = hero;

        stage = new GameStage(hero);
        Gdx.input.setInputProcessor(stage);
    }

    public void setShowingInventory(boolean isShowingInventory) {
        this.isShowingInventory = isShowingInventory;
    }


    public void render(Batch uiBatch, Batch cameraBatch, OrthographicCamera camera) {

        uiBatch.begin();
        uiBatch.draw(UISpriteHolder.portraitPanel, 0, Values.SCREEN_HEIGHT - UISpriteHolder.portraitPanel.getRegionHeight());
        uiBatch.end();

        //todo: переправить к чертям все от сих и дальше:
        Vector3 inventoryOrigin = camera.unproject(new Vector3(INVENTORY_MARGIN_LEFT, INVENTORY_MARGIN_TOP, 0));
        Vector3 panelOrigin = camera.unproject(new Vector3(PANEL_MARGIN_LEFT, PANEL_MARGIN_TOP, 0));

        cameraBatch.begin();

        int selectedCellIndex = hero.getPanel().getSelectedIndex();
        for (int i = 0; i < PANEL_SIZE; i++) {
            cameraBatch.draw(SpriteHolder.inventoryCell,
                    panelOrigin.x + i * (2 + 0.1f),
                    panelOrigin.y,
                    2, 2);
            if (i == selectedCellIndex)
                cameraBatch.draw(UISpriteHolder.panelSelectedCell,
                        panelOrigin.x + i * (2 + 0.1f),
                        panelOrigin.y,
                        2, 2);
        }

        if (isShowingInventory) {
            for (int i = 0; i < INVENTORY_SIZE_HEIGHT; i++)
                for (int j = 0; j < INVENTORY_SIZE_WIDTH; j++) {
                    cameraBatch.draw(SpriteHolder.inventoryCell,
                            inventoryOrigin.x + j * (2 + 0.1f),
                            inventoryOrigin.y + i * (2 + 0.1f),
                            2, 2);
                }

            int i = 0;
            int j = 0;
            for (Collectible item : hero.getInventory().getData()) {
                if (item != null) {
                    cameraBatch.draw(SpriteHolder.getTexture(item.getId()),
                            inventoryOrigin.x + j * (2 + 0.1f),
                            inventoryOrigin.y + i * (2 + 0.1f),
                            2, 2);

                    j++;
                    if (j == INVENTORY_SIZE_WIDTH) {
                        i++;
                        j = 0;
                    }
                }
            }
        }


        cameraBatch.end();

        stage.act();
        stage.draw();
    }
}