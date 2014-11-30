package ru.vsu.csf.twopeoplestudios.renderers;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import ru.vsu.csf.twopeoplestudios.model.characters.Hero;
import ru.vsu.csf.twopeoplestudios.model.collectibles.Collectible;
import ru.vsu.csf.twopeoplestudios.renderers.ui.UISpriteHolder;

public class UiRenderer {

    private static float INVENTORY_MARGIN_LEFT = 200;
    private static float INVENTORY_MARGIN_TOP = 800;
    private static float PANEL_MARGIN_LEFT = 400;
    private static float PANEL_MARGIN_TOP = 1020;

    private static int INVENTORY_SIZE_WIDTH = 10;
    private static int INVENTORY_SIZE_HEIGHT = 4;
    private static int PANEL_SIZE = 10;

    public Hero hero;

    private boolean isShowingInventory;

    public UiRenderer(Hero hero) {
        this.hero = hero;
    }

    public void setShowingInventory(boolean isShowingInventory) {
        this.isShowingInventory = isShowingInventory;
    }

    public void render(Batch batch, OrthographicCamera camera) {

        Vector3 inventoryOrigin = camera.unproject(new Vector3(INVENTORY_MARGIN_LEFT, INVENTORY_MARGIN_TOP, 0));
        Vector3 panelOrigin = camera.unproject(new Vector3(PANEL_MARGIN_LEFT, PANEL_MARGIN_TOP, 0));

        batch.begin();

        int selectedCellIndex = hero.getPanel().getSelectedIndex();
        for (int i = 0; i < PANEL_SIZE; i++) {
            batch.draw(SpriteHolder.inventoryCell,
                    panelOrigin.x + i * (2 + 0.1f),
                    panelOrigin.y,
                    2, 2);
            if (i == selectedCellIndex)
                batch.draw(UISpriteHolder.panelSelectedCell,
                        panelOrigin.x + i * (2 + 0.1f),
                        panelOrigin.y,
                        2, 2);
        }

        if (isShowingInventory) {
            for (int i = 0; i < INVENTORY_SIZE_HEIGHT; i++)
                for (int j = 0; j < INVENTORY_SIZE_WIDTH; j++) {
                    batch.draw(SpriteHolder.inventoryCell,
                            inventoryOrigin.x + j * (2 + 0.1f),
                            inventoryOrigin.y + i * (2 + 0.1f),
                            2, 2);
                }

            int i = 0;
            int j = 0;
            for (Collectible item : hero.getInventory().getData()) {
                if (item != null) {
                    batch.draw(SpriteHolder.getTexture(item.getId()),
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


        batch.end();
    }
}