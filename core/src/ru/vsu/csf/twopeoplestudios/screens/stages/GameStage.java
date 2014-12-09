package ru.vsu.csf.twopeoplestudios.screens.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.vsu.csf.twopeoplestudios.Values;
import ru.vsu.csf.twopeoplestudios.model.characters.Hero;
import ru.vsu.csf.twopeoplestudios.model.craft.Recipe;
import ru.vsu.csf.twopeoplestudios.model.craft.Recipes;
import ru.vsu.csf.twopeoplestudios.renderers.UiRenderer;
import ru.vsu.csf.twopeoplestudios.renderers.ui.CraftRecipe;
import ru.vsu.csf.twopeoplestudios.renderers.ui.UISpriteHolder;

import java.util.*;
import java.util.List;

public class GameStage extends Stage {

    private Hero hero;
    private UiRenderer uiRenderer;

    private Table mainTable;
    private Table craftingTable;
    private int craftRecipeIndex = -1;

    private ProgressBar hp;
    private ProgressBar hg;
    private ProgressBar st;

    private Table scrollTable;
    private ScrollPane pane;

    public GameStage(Hero hero, UiRenderer uiRenderer) {

        this.hero = hero;
        this.uiRenderer = uiRenderer;

        //setDebugAll(true);
        mainTable = new Table() {{
            setBounds(0, 0, Values.SCREEN_WIDTH, Values.SCREEN_HEIGHT);
        }};
        craftingTable = new Table() {{
            setBounds(300, 330, 800, 500);
            //setDebug(true);
            setVisible(false);
            setBackground(new TextureRegionDrawable(new TextureRegion(UISpriteHolder.inventoryBg.getTexture())));
        }};

        hp = new ProgressBar(0, 100, 1, false, UISpriteHolder.progressBarHeroHPStyle);
        hg = new ProgressBar(0, 100, 1, false, UISpriteHolder.progressBarHeroHGStyle);
        st = new ProgressBar(0, 100, 1, false, UISpriteHolder.progressBarHeroSTStyle);

        hp.setValue(100);
        hg.setValue(100);
        st.setValue(100);

        mainTable.left().top().add(hp).padLeft(310).padTop(30).width(500).left().row();
        mainTable.add(hg).padLeft(310).padTop(12).width(500).left().row();
        mainTable.add(st).padLeft(310).width(500).padTop(12).left().row();

        /*final List.ListStyle style = new List.ListStyle() {{
            font = UISpriteHolder.font32;
            fontColorSelected = Color.WHITE;
            fontColorUnselected = Color.WHITE;
            selection = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("gfx/ui/test/selection.png"))));
        }};*/
        final ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();

        /*Skin s = new Skin() {{
            add("default", style, List.ListStyle.class);
            *//*add("font", UISpriteHolder.font32);
            add("fontColorSelected", Color.WHITE);
            add("fontColorUnselected", Color.WHITE);
            add("selection", new Texture(Gdx.files.internal("gfx/ui/test/selection.png")));*//*
        }};*/
        Skin scrollSkin = new Skin() {{
            //scrollPaneStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("gfx/ui/test/selection.png"))));
            add("default", scrollPaneStyle, ScrollPane.ScrollPaneStyle.class);
        }};

        scrollTable = new Table() {{
            /*add(new CraftRecipe(new LinkedList<Integer>() {{
                add(102);
                add(103);
            }}, 106));
            row();
            add(new CraftRecipe(new LinkedList<Integer>() {{
                add(101);
                add(100);
            }}, 105));
            row();
            add(new CraftRecipe(new LinkedList<Integer>() {{
                add(101);
                add(100);
            }}, 105));
            row();
            add(new CraftRecipe(new LinkedList<Integer>() {{
                add(102);
                add(101);
            }}, 105));
            row();
            add(new CraftRecipe(new LinkedList<Integer>() {{
                add(103);
                add(102);
            }}, 105));
            row();
            add(new CraftRecipe(new LinkedList<Integer>() {{
                add(103);
                add(105);
            }}, 102));
            row();
            add(new CraftRecipe(new LinkedList<Integer>() {{
                add(103);
                add(104);
            }}, 101));
            row();
            add(new CraftRecipe(new LinkedList<Integer>() {{
                add(101);
                add(100);
            }}, 105));
            row();
            add(new CraftRecipe(new LinkedList<Integer>() {{
                add(102);
                add(101);
            }}, 105));
            row();
            add(new CraftRecipe(new LinkedList<Integer>() {{
                add(103);
                add(102);
            }}, 105));
            row();
            add(new CraftRecipe(new LinkedList<Integer>() {{
                add(103);
                add(105);
            }}, 102));
            row();
            add(new CraftRecipe(new LinkedList<Integer>() {{
                add(103);
                add(104);
            }}, 101));
            row();
            add(new CraftRecipe(new LinkedList<Integer>() {{
                add(101);
                add(100);
            }}, 105));
            row();
            add(new CraftRecipe(new LinkedList<Integer>() {{
                add(102);
                add(101);
            }}, 105));
            row();
            add(new CraftRecipe(new LinkedList<Integer>() {{
                add(103);
                add(102);
            }}, 105));
            row();
            add(new CraftRecipe(new LinkedList<Integer>() {{
                add(103);
                add(105);
            }}, 102));
            row();
            add(new CraftRecipe(new LinkedList<Integer>() {{
                add(103);
                add(104);
            }}, 101));*/
        }};

        pane = new ScrollPane(scrollTable, scrollSkin);
        pane.setBounds(100, 100, 600, 400);
        pane.setOverscroll(false, false);
        pane.setScrollingDisabled(true, false);
        pane.setLayoutEnabled(true);
        pane.setCancelTouchFocus(true);
        pane.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                y = pane.getHeight() - y;
                y += pane.getScrollY();

                int selectedItem = (int) (y / CraftRecipe.ITEM_HEIGHT);
                Gdx.app.log("", "X: " + x + "; Y:" + y + "; Selected:" + selectedItem);

                for (int i = 0; i < scrollTable.getChildren().size; i++) {
                    ((CraftRecipe) scrollTable.getChildren().get(i)).unselect();
                }
                ((CraftRecipe) scrollTable.getChildren().get(selectedItem)).select();
                craftRecipeIndex = selectedItem;
            }
        });
        pane.layout();
        pane.invalidate();

        TextButton craftBtn = new TextButton("Craft", UISpriteHolder.textButtonStyle) {{
            addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //todo: onClick
                }
            });
        }};

        craftingTable.add(pane).pad(15).row();
        craftingTable.add(craftBtn).height(70).bottom();

        addActor(mainTable);
        addActor(craftingTable);
    }

    @Override
    public boolean keyDown(int keyCode) {
        hero.keyDown(keyCode);
        return true;
    }

    @Override
    public boolean keyUp(int keyCode) {
        hero.keyUp(keyCode);
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        switch (character) {
            case 'i':
            case 'ш':
                uiRenderer.toggleInventory();
                break;
            case 't':
            case 'е':
                if (craftingTable.isVisible())
                    craftingTable.setVisible(false);
                else {
                    updateCraftTable();
                    craftingTable.setVisible(true);
                }
                break;
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '0':
                hero.getPanel().selectItem(Integer.parseInt(String.valueOf(character)));
                break;
            default:
                hero.keyTyped(character);
        }
        return true;
    }

    private void updateCraftTable() {
        //todo: add ids to recipes
        List<Recipe> data = Recipes.get().getAllPossibleProducts(hero.getInventory());
        scrollTable.clear();
        for (Recipe r : data) {
            scrollTable.add(new CraftRecipe(r)).expandX().row();
        }
        scrollTable.layout();
        scrollTable.invalidate();
    }

    //region Handling input
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (!super.touchDown(screenX, screenY, pointer, button))
        switch (button) {
            case Input.Buttons.LEFT:
                if (uiRenderer.isShowingInventory()) {
                    uiRenderer.onClick(screenX, Values.SCREEN_HEIGHT - screenY);
                }
                break;
            case Input.Buttons.RIGHT:
                if (uiRenderer.isShowingInventory()) {
                    uiRenderer.onRMBClick(screenX, Values.SCREEN_HEIGHT - screenY);
                }
                break;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(int amount) {
        return super.scrolled(amount);
    }
    //endregion

    @Override
    public void act(float delta) {
        hp.setValue(hero.getHp());
        hg.setValue(hero.getHunger());
        st.setValue(hero.getStamina());

        pane.act(delta);
        super.act(delta);
    }

    @Override
    public void act() {
        super.act();
    }
}
