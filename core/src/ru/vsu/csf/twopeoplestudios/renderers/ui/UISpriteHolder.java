package ru.vsu.csf.twopeoplestudios.renderers.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Disposable;

public class UISpriteHolder implements Disposable {

    private static final float HERO_STAT_PROGRESS_BAR_HEIGHT = 20;

    public static BitmapFont font32;
    public static BitmapFont font32Green;

    public static TextureAtlas atlasBtns;
    public static TextureAtlas atlasPrgBar;
    public static TextureAtlas atlasHeroStatsPrgBars;
    public static TextureAtlas atlasInventoryCell;
    public static TextureAtlas atlasInvBg;
    public static TextureAtlas atlasCraftItem;

    public static Skin skinBtns;
    public static Skin skinPrgBar;
    public static Skin skinHeroStatsPrgBar;
    public static Skin skinInvCell;
    public static Skin skinInvBg;
    public static Skin skinLbls;
    public static Skin skinGreenLbls;
    public static Skin skinCraftItem;

    public static TextButton.TextButtonStyle textButtonStyle;
    public static ProgressBar.ProgressBarStyle progressBarStyle;
    public static ProgressBar.ProgressBarStyle progressBarHeroHPStyle;
    public static ProgressBar.ProgressBarStyle progressBarHeroHGStyle;
    public static ProgressBar.ProgressBarStyle progressBarHeroSTStyle;
    public static Label.LabelStyle labelStyle;

    public static NinePatch inventoryCell;
    public static NinePatch panelSelectedCell;
    public static TextureRegion portraitPanel;
    public static NinePatch inventoryBg;

    public static void init() {
        font32 = new BitmapFont(Gdx.files.internal("gfx/fonts/main32.fnt"), false); //подгрузка готового шрифта из файла
        font32Green = new BitmapFont(Gdx.files.internal("gfx/fonts/main32Green.fnt"), false);

        final Label.LabelStyle labelStyle1 = new Label.LabelStyle() {{
            font = font32;
        }};
        final Label.LabelStyle greenLabelStyle = new Label.LabelStyle() {{
            font = font32Green;
        }};

        atlasBtns = new TextureAtlas(Gdx.files.internal("gfx/ui/button/button.pack")); //класс для загрузки текстурных паков - здеся атласы
        atlasPrgBar = new TextureAtlas(Gdx.files.internal("gfx/ui/progress_bar/progress_bar.pack"));
        atlasHeroStatsPrgBars = new TextureAtlas(Gdx.files.internal("gfx/ui/state_progress_bar/state_progress_bar.pack"));
        atlasInventoryCell = new TextureAtlas(Gdx.files.internal("gfx/ui/inventory_cell/inventory_cell.pack"));
        atlasInvBg = new TextureAtlas(Gdx.files.internal("gfx/ui/inventory_bg/inv_bg.pack"));
        atlasCraftItem = new TextureAtlas(Gdx.files.internal("gfx/ui/craftItem/craftItem.pack"));

        skinBtns = new Skin(atlasBtns); //считай, стиль. Применяется к разным UI-элементам
        skinPrgBar = new Skin(atlasPrgBar);
        skinHeroStatsPrgBar = new Skin(atlasHeroStatsPrgBars);
        skinInvCell = new Skin(atlasInventoryCell);
        skinInvBg = new Skin(atlasInvBg);
        skinLbls = new Skin() {{
            add("default", labelStyle1, Label.LabelStyle.class);
        }};
        skinGreenLbls = new Skin() {{
            add("default", greenLabelStyle, Label.LabelStyle.class);
        }};
        skinCraftItem = new Skin(atlasCraftItem);

        textButtonStyle = new TextButton.TextButtonStyle() {{ //стиль, применяющийся для создаваемых кнопок
            up = skinBtns.getDrawable("button.normal"); //сами строки эти можно подсмотреть в *.pack-файле
            down = skinBtns.getDrawable("button.pressed");
            pressedOffsetX = 2; //величина эффекта вдавливания кнопки при нажатии
            pressedOffsetY = -2;
            font = font32;
        }};
        progressBarStyle = new ProgressBar.ProgressBarStyle() {{
            background = skinPrgBar.getDrawable("prBarEmpty");
            background.setMinHeight(15);
            knobBefore = skinPrgBar.getDrawable("prBarFilled");
            knobBefore.setMinHeight(15);
        }};
        progressBarHeroHPStyle = new ProgressBar.ProgressBarStyle() {{
            background = skinHeroStatsPrgBar.getDrawable("state_progress_bar_empty");
            knobBefore = skinHeroStatsPrgBar.getDrawable("state_progress_bar_red");
            background.setMinHeight(HERO_STAT_PROGRESS_BAR_HEIGHT);
            knobBefore.setMinHeight(HERO_STAT_PROGRESS_BAR_HEIGHT);
        }};
        progressBarHeroHGStyle = new ProgressBar.ProgressBarStyle() {{
            background = skinHeroStatsPrgBar.getDrawable("state_progress_bar_empty");
            knobBefore = skinHeroStatsPrgBar.getDrawable("state_progress_bar_yellow");
            background.setMinHeight(HERO_STAT_PROGRESS_BAR_HEIGHT);
            knobBefore.setMinHeight(HERO_STAT_PROGRESS_BAR_HEIGHT);
        }};
        progressBarHeroSTStyle = new ProgressBar.ProgressBarStyle() {{
            background = skinHeroStatsPrgBar.getDrawable("state_progress_bar_empty");
            knobBefore = skinHeroStatsPrgBar.getDrawable("state_progress_bar_green");
            background.setMinHeight(HERO_STAT_PROGRESS_BAR_HEIGHT);
            knobBefore.setMinHeight(HERO_STAT_PROGRESS_BAR_HEIGHT);
        }};
        labelStyle = new Label.LabelStyle() {{
            font = font32;
            fontColor = Color.BLACK;
        }};


        inventoryBg = new NinePatch(skinInvBg.getPatch("interface_pan"));
        inventoryCell = new NinePatch(skinInvCell.getPatch("inventory_cell"));
        panelSelectedCell = new NinePatch(skinInvCell.getPatch("panel_cell_selection"));

        portraitPanel = new TextureRegion(new Texture(Gdx.files.internal("gfx/ui/portraitStates.png")));
    }

    private UISpriteHolder() {

    }


    @Override
    public void dispose() {
        skinHeroStatsPrgBar.dispose();
        skinInvCell.dispose();
        skinPrgBar.dispose();
        skinBtns.dispose();
        skinInvBg.dispose();

        atlasInventoryCell.dispose();
        atlasHeroStatsPrgBars.dispose();
        atlasPrgBar.dispose();
        atlasBtns.dispose();
        atlasInvBg.dispose();
    }
}
