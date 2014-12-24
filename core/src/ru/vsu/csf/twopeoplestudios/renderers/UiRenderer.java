package ru.vsu.csf.twopeoplestudios.renderers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import ru.vsu.csf.twopeoplestudios.Values;
import ru.vsu.csf.twopeoplestudios.model.characters.Hero;
import ru.vsu.csf.twopeoplestudios.model.characters.effects.EffectSpriteHolder;
import ru.vsu.csf.twopeoplestudios.model.collectibles.*;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.HerbProperty;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.Herbs;
import ru.vsu.csf.twopeoplestudios.model.craft.Recipes;
import ru.vsu.csf.twopeoplestudios.renderers.ui.UISpriteHolder;
import ru.vsu.csf.twopeoplestudios.screens.stages.GameStage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class UiRenderer {

    private static final float INVENTORY_MARGIN_LEFT = 220f;
    private static final float INVENTORY_MARGIN_BOTTOM = 350;
    private static final DecimalFormat STATS_FORMAT = new DecimalFormat("###.#");
    private static final DecimalFormat SECONDS_FORMAT = new DecimalFormat("###");

    private static final float PANEL_MARGIN_LEFT = (1280 - 700) / 2f;
    private static final float PANEL_MARGIN_BOTTOM = 0;

    private static final float PANEL_CELL_SIZE = 70;
    private static final float INVENTORY_CELL_SIZE = 65;

    private static final int PANEL_SIZE = 10;

    private static final float SUBPANEL_WIDTH = 250;
    private static final float SUBPANEL_HEIGHT = Inventory.HEIGHT * INVENTORY_CELL_SIZE;

    public Hero hero;

    private GameStage stage;

    private boolean isShowingInventory;

    public boolean isShowingInventory() {
        return isShowingInventory;
    }

    public UiRenderer(Hero hero, GameStage stage) {
        this.hero = hero;

        this.stage = stage;// = new GameStage(hero, this);
        Gdx.input.setInputProcessor(stage);
    }

    public void render(Batch uiBatch, float delta) {
        uiBatch.begin();
        uiBatch.draw(UISpriteHolder.portraitPanel, 0, Values.SCREEN_HEIGHT - UISpriteHolder.portraitPanel.getRegionHeight());

        //region Panel
        int selectedCellIndex = hero.getPanel().getSelectedIndex();
        for (int i = 0; i < PANEL_SIZE; i++) {
            UISpriteHolder.inventoryCell.draw(uiBatch,
                    PANEL_MARGIN_LEFT + i * (PANEL_CELL_SIZE),
                    PANEL_MARGIN_BOTTOM,
                    PANEL_CELL_SIZE, PANEL_CELL_SIZE);

            if (i == selectedCellIndex)
                UISpriteHolder.panelSelectedCell.draw(uiBatch,
                        PANEL_MARGIN_LEFT + i * (PANEL_CELL_SIZE),
                        PANEL_MARGIN_BOTTOM,
                        PANEL_CELL_SIZE, PANEL_CELL_SIZE);
        }

        int pnl_cnt = 0;
        for (Collectible c : hero.getPanel().getData()) {
            if (c != null) {
                uiBatch.draw(Items.getInstance().getItemTexture(c.getId()),
                        PANEL_MARGIN_LEFT + pnl_cnt*PANEL_CELL_SIZE,
                        PANEL_MARGIN_BOTTOM,
                        PANEL_CELL_SIZE,
                        PANEL_CELL_SIZE);

                if (Items.getInstance().checkIfCountable(c.getId()))
                    UISpriteHolder.font32.draw(uiBatch, "x" + c.getCount(),
                            PANEL_MARGIN_LEFT + pnl_cnt * (PANEL_CELL_SIZE) + 20,
                            PANEL_MARGIN_BOTTOM + 20);

                pnl_cnt++;
            }
        }
        //endregion

        //region Inventory
        if (isShowingInventory) {

            for (int i = 0; i < Inventory.HEIGHT; i++) //cells
                for (int j = 0; j < Inventory.WIDTH; j++) {
                    UISpriteHolder.inventoryCell.draw(uiBatch,
                            INVENTORY_MARGIN_LEFT + j * (INVENTORY_CELL_SIZE),
                            INVENTORY_MARGIN_BOTTOM + i * (INVENTORY_CELL_SIZE),
                            INVENTORY_CELL_SIZE, INVENTORY_CELL_SIZE);
                }

            UISpriteHolder.panelSelectedCell.draw(uiBatch, //selected cell
                    INVENTORY_MARGIN_LEFT + hero.getInventory().selectedColumn * (INVENTORY_CELL_SIZE),
                    INVENTORY_MARGIN_BOTTOM + hero.getInventory().selectedRow * (INVENTORY_CELL_SIZE),
                    INVENTORY_CELL_SIZE, INVENTORY_CELL_SIZE);

            int i = 0;
            int j = 0;
            for (Collectible item : hero.getInventory().getData()) { //items
                if (item != null) {

                    uiBatch.draw(Items.getInstance().getItemTexture(item.getId()),
                            INVENTORY_MARGIN_LEFT + j * (INVENTORY_CELL_SIZE) + 5,
                            INVENTORY_MARGIN_BOTTOM + i * (INVENTORY_CELL_SIZE) + 5,
                            INVENTORY_CELL_SIZE - 10, INVENTORY_CELL_SIZE - 10);

                    if (Items.getInstance().checkIfCountable(item.getId()))
                        UISpriteHolder.font32.draw(uiBatch, "x" + item.getCount(),
                                INVENTORY_MARGIN_LEFT + j * (INVENTORY_CELL_SIZE) + 20,
                                INVENTORY_MARGIN_BOTTOM + i * (INVENTORY_CELL_SIZE) + 20);

                }

                j++;
                if (j == Inventory.WIDTH) {
                    i++;
                    j = 0;
                }
            }

            if (isDraggingItem()) {
                uiBatch.draw(Items.getInstance().getItemTexture(draggedItem.getId()),
                        draggedItemPos.x,
                        draggedItemPos.y,
                        INVENTORY_CELL_SIZE,
                        INVENTORY_CELL_SIZE);
            }

            //region Info subpanel
            float x = INVENTORY_MARGIN_LEFT + Inventory.WIDTH * INVENTORY_CELL_SIZE;
            UISpriteHolder.inventoryCell.draw(uiBatch,
                    x,
                    INVENTORY_MARGIN_BOTTOM,
                    SUBPANEL_WIDTH,
                    SUBPANEL_HEIGHT);

            Collectible item = hero.getInventory().getSelectedItem();
            if (item != null) {
                uiBatch.draw(Items.getInstance().getItemTexture(item.getId()), //icon
                        x + 10,
                        SUBPANEL_HEIGHT + INVENTORY_MARGIN_BOTTOM - 10 - 50,
                        50,
                        50
                );

                UISpriteHolder.font32.draw(uiBatch,                             //name
                        Items.getInstance().getItemName(item.getId()),
                        x + 10 + 50 + 10, //margin, gfx size, margin
                        SUBPANEL_HEIGHT + INVENTORY_MARGIN_BOTTOM - 25);


                if (Items.getInstance().isHerb(item.getId())) {

                    UISpriteHolder.font32.draw(uiBatch,
                            "Known properties:",
                            x + 10,
                            SUBPANEL_HEIGHT + INVENTORY_MARGIN_BOTTOM - 70);

                    for (int cnt = 0; cnt < Herbs.getInstance().getPropertiesOfHerb(item.getId()).size(); cnt++) {
                        UISpriteHolder.inventoryBg.draw(uiBatch,
                                x + 10 + cnt*60,
                                SUBPANEL_HEIGHT + INVENTORY_MARGIN_BOTTOM - 150,
                                50, 50);
                    }

                    ArrayList<HerbProperty> knownProps = hero.getKnownPropertiesOfHerb(item.getId());
                    if (knownProps != null)
                        for (int knownPropCnt = 0; knownPropCnt < knownProps.size(); knownPropCnt++) {
                            uiBatch.draw(EffectSpriteHolder.getInstance().getTexture(knownProps.get(knownPropCnt)),
                                    x + 10 + knownPropCnt*60,
                                    SUBPANEL_HEIGHT + INVENTORY_MARGIN_BOTTOM - 150,
                                    50, 50);
                        }
                }
                else if (Recipes.get().ifItemTransformable(item.getId())) {  // craft
                    List<Integer> transforms = Recipes.get().getIDsOfTransformResult(item.getId());
                    for (int t = 0; t < transforms.size(); t++) {
                        UISpriteHolder.inventoryBg.draw(uiBatch,
                                x + 10 + t*60,
                                SUBPANEL_HEIGHT + INVENTORY_MARGIN_BOTTOM - 150,
                                50, 50);

                        uiBatch.draw(Items.getInstance().getItemTexture(transforms.get(t)),
                                x + 10 + t*60,
                                SUBPANEL_HEIGHT + INVENTORY_MARGIN_BOTTOM - 150,
                                50, 50);
                    }
                }
            }
            //endregion
        }
        //endregion

        //region Buffs
        for (int buffCnt = 0; buffCnt < hero.getActiveEffects().size(); buffCnt++) {
            uiBatch.draw(EffectSpriteHolder.getInstance().getTexture(hero.getActiveEffects().get(buffCnt).property),
                    UISpriteHolder.portraitPanel.getRegionWidth() + 40 + buffCnt*50,
                    Values.SCREEN_HEIGHT - 60,
                    40, 40);

            UISpriteHolder.font32.draw(uiBatch,
                    SECONDS_FORMAT.format(hero.getActiveEffects().get(buffCnt).duration) + "s",
                    UISpriteHolder.portraitPanel.getRegionWidth() + 40 + buffCnt*50,
                    Values.SCREEN_HEIGHT - 80);
        }
        //endregion

        uiBatch.end();

        stage.act(delta);
        stage.draw();

        uiBatch.begin();
        UISpriteHolder.font32.draw(uiBatch,
                STATS_FORMAT.format(hero.getHp()) + " / " + hero.getMaxHp(),
                475,
                Values.SCREEN_HEIGHT - 30);
        UISpriteHolder.font32.draw(uiBatch,
                STATS_FORMAT.format(hero.getHunger()) + " / " + hero.getMaxFl(),
                475,
                Values.SCREEN_HEIGHT - 30 - 33);
        UISpriteHolder.font32.draw(uiBatch,
                STATS_FORMAT.format(hero.getStamina()) + " / " + hero.getMaxSt(),
                475,
                Values.SCREEN_HEIGHT - 30 - 2*33);
        uiBatch.end();
    }

    private Vector2 getInventoryCell(float screenX, float screenY) {
        Vector2 result = new Vector2();

        screenX -= INVENTORY_MARGIN_LEFT;
        screenY -= INVENTORY_MARGIN_BOTTOM;

        if (screenX < 0 || screenY < 0)
            return null;

        result.x = (int) (screenX / INVENTORY_CELL_SIZE);
        result.y = (int) (screenY / INVENTORY_CELL_SIZE);

        return (result.x < Inventory.WIDTH && result.y < Inventory.HEIGHT)? result : null;
    }

    private Vector2 getPanelCell(float screenX, float screenY) {
        Vector2 result = new Vector2();

        screenX -= PANEL_MARGIN_LEFT;
        screenY -= PANEL_MARGIN_BOTTOM;

        if (screenX < 0 || screenY < 0)
            return null;

        result.x = (int) (screenX / PANEL_CELL_SIZE);
        result.y = (int) (screenY / PANEL_CELL_SIZE);

        return (result.x < Panel.WIDTH && result.y < Panel.HEIGHT)? result : null;
    }

    private boolean isDraggingItem;
    private Vector2 draggedItemShift;
    private Vector2 draggedItemPos;
    private Collectible draggedItem;
    private Vector2 oldDraggingItemPos;
    private boolean draggedItemFromInventory; //false is for panel

    public boolean isDraggingItem() {
        return isDraggingItem;
    }

    public boolean onClick(int screenX, int screenY) {
        Vector2 cell = getInventoryCell(screenX, screenY);

        if (cell != null) {
            hero.getInventory().selectItem((int) cell.x, (int) cell.y);
            if (!hero.getInventory().isSelectedCellEmpty()) {
                isDraggingItem = true;

                oldDraggingItemPos = hero.getInventory().getSelectedIndexes();

                draggedItemPos = hero.getInventory().getSelectedIndexes().scl(INVENTORY_CELL_SIZE).add(INVENTORY_MARGIN_LEFT, INVENTORY_MARGIN_BOTTOM);
                draggedItemShift = new Vector2(screenX, screenY).sub(draggedItemPos);

                draggedItem = hero.getInventory().getSelectedItem();
                hero.getInventory().destroyItemInSelectedIndex();

                draggedItemFromInventory = true;
            }
            return true;
        }

        cell = getPanelCell(screenX, screenY);
        if (cell != null) {
            hero.getPanel().setSelectedIndex((int) cell.x);
            if (!hero.getPanel().isSelectedCellEmpty()) {
                isDraggingItem = true;

                oldDraggingItemPos = new Vector2(hero.getPanel().getSelectedIndex(), 0);

                draggedItemPos = new Vector2(hero.getPanel().getSelectedIndex(), 0).scl(PANEL_CELL_SIZE).add(PANEL_MARGIN_LEFT, PANEL_MARGIN_BOTTOM);
                draggedItemShift = new Vector2(screenX, screenY).sub(draggedItemPos);

                draggedItem = hero.getPanel().getSelectedItem();
                hero.getPanel().destroyItemInSelectedIndex();

                draggedItemFromInventory = false;
            }
            return true;
        }
        return false;
    }

    public void updateDraggingItem(int screenX, int screenY) {
        draggedItemPos.set(screenX, screenY).sub(draggedItemShift);
    }

    public void onMouseUp() {
        if (isDraggingItem) {
            isDraggingItem = false;

            Inventory inventory = hero.getInventory();

            Vector2 cell = getInventoryCell(draggedItemPos.x + draggedItemShift.x, draggedItemPos.y + draggedItemShift.y);
            if (cell != null) {
                inventory.selectItem((int) cell.x, (int) cell.y);
                if (!inventory.isSelectedCellEmpty())
                    inventory.selectItem((int) oldDraggingItemPos.x, (int) oldDraggingItemPos.y);

                inventory.putInSelectedCell(draggedItem);
            }
            else {
                cell = getPanelCell(draggedItemPos.x + draggedItemShift.x, draggedItemPos.y + draggedItemShift.y);
                if (cell != null) {
                    hero.getPanel().setSelectedIndex((int) cell.x);
                    if (!hero.getPanel().isSelectedCellEmpty())
                        hero.getPanel().setSelectedIndex((int) oldDraggingItemPos.x);

                    hero.getPanel().putInSelectedCell(draggedItem);
                }
                else if (draggedItemFromInventory) {
                    inventory.selectItem((int) oldDraggingItemPos.x, (int) oldDraggingItemPos.y);
                    inventory.putInSelectedCell(draggedItem);
                }
                else { //from panel
                    hero.getPanel().setSelectedIndex((int) oldDraggingItemPos.x);
                    hero.getPanel().putInSelectedCell(draggedItem);
                }
            }

            draggedItem = null;
        }
    }

    public void onRMBClick(int screenX, int screenY) {
        Vector2 cell = getInventoryCell(screenX, screenY);
        if (cell != null) {
            hero.getInventory().selectItem((int) cell.x, (int) cell.y);
            if (!hero.getInventory().isSelectedCellEmpty()) {
                hero.getInventory().consume();
                return;
            }
        }
        cell = getPanelCell(screenX, screenY);
        if (cell != null) {
            hero.getPanel().setSelectedIndex((int) cell.x);
            if (!hero.getPanel().isSelectedCellEmpty()) {
                hero.getPanel().consume();
            }
        }
    }


    public void toggleInventory() {
        this.isShowingInventory = !this.isShowingInventory;
    }
}