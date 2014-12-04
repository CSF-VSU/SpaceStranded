package ru.vsu.csf.twopeoplestudios.renderers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import ru.vsu.csf.twopeoplestudios.Values;
import ru.vsu.csf.twopeoplestudios.model.characters.Hero;
import ru.vsu.csf.twopeoplestudios.model.collectibles.Collectible;
import ru.vsu.csf.twopeoplestudios.model.collectibles.Items;
import ru.vsu.csf.twopeoplestudios.renderers.ui.UISpriteHolder;
import ru.vsu.csf.twopeoplestudios.screens.stages.GameStage;

public class UiRenderer {

    private static final float INVENTORY_MARGIN_LEFT = (1280 - 650) / 2f;
    private static final float INVENTORY_MARGIN_BOTTOM = 350;

    private static final float PANEL_MARGIN_LEFT = (1280 - 700) / 2f;
    private static final float PANEL_MARGIN_BOTTOM = 0;

    private static final float PANEL_CELL_SIZE = 70;
    private static final float INVENTORY_CELL_SIZE = 65;

    private static final int INVENTORY_SIZE_WIDTH = 10;
    private static final int INVENTORY_SIZE_HEIGHT = 4;
    private static final int PANEL_SIZE = 10;

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
        //this.stage.setShowingInventory(isShowingInventory);
    }


    public void render(Batch uiBatch) {
        uiBatch.begin();
        uiBatch.draw(UISpriteHolder.portraitPanel, 0, Values.SCREEN_HEIGHT - UISpriteHolder.portraitPanel.getRegionHeight());

        int selectedCellIndex = hero.getPanel().getSelectedIndex();
        for (int i = 0; i < PANEL_SIZE; i++) {
            uiBatch.draw(UISpriteHolder.inventoryCell,
                    PANEL_MARGIN_LEFT + i * (PANEL_CELL_SIZE),
                    PANEL_MARGIN_BOTTOM,
                    PANEL_CELL_SIZE, PANEL_CELL_SIZE);

            if (i == selectedCellIndex)
                uiBatch.draw(UISpriteHolder.panelSelectedCell,
                        PANEL_MARGIN_LEFT + i * (PANEL_CELL_SIZE),
                        PANEL_MARGIN_BOTTOM,
                        PANEL_CELL_SIZE, PANEL_CELL_SIZE);
        }

        if (isShowingInventory) {
            for (int i = 0; i < INVENTORY_SIZE_HEIGHT; i++)
                for (int j = 0; j < INVENTORY_SIZE_WIDTH; j++) {
                    uiBatch.draw(UISpriteHolder.inventoryCell,
                            INVENTORY_MARGIN_LEFT + j * (INVENTORY_CELL_SIZE),
                            INVENTORY_MARGIN_BOTTOM + i * (INVENTORY_CELL_SIZE),
                            INVENTORY_CELL_SIZE, INVENTORY_CELL_SIZE);
                }

            int i = 0;
            int j = 0;
            for (Collectible item : hero.getInventory().getData()) {
                if (item != null) {

                    uiBatch.draw(Items.getInstance().getItemTexture(item.getId()),
                            INVENTORY_MARGIN_LEFT + j * (INVENTORY_CELL_SIZE) + 5,
                            INVENTORY_MARGIN_BOTTOM + i * (INVENTORY_CELL_SIZE) + 5,
                            INVENTORY_CELL_SIZE - 10, INVENTORY_CELL_SIZE - 10);

                    if (Items.getInstance().checkIfCountable(item.getId()))
                        UISpriteHolder.font32.draw(uiBatch, "x" + item.getCount(),
                                INVENTORY_MARGIN_LEFT + j * (INVENTORY_CELL_SIZE) + 20,
                                INVENTORY_MARGIN_BOTTOM + i * (INVENTORY_CELL_SIZE) + 20);

                    j++;
                    if (j == INVENTORY_SIZE_WIDTH) {
                        i++;
                        j = 0;
                    }
                }
            }
        }

        uiBatch.end();

        stage.act();
        stage.draw();
    }
}