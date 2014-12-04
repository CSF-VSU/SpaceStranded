package ru.vsu.csf.twopeoplestudios.screens.stages;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import ru.vsu.csf.twopeoplestudios.Values;
import ru.vsu.csf.twopeoplestudios.model.characters.Hero;
import ru.vsu.csf.twopeoplestudios.renderers.ui.UISpriteHolder;

public class GameStage extends Stage {

    private Hero hero;

    private Table mainTable;
    private Table inventoryTable;

    private Label inventoryTitle;

    private ProgressBar hp;
    private ProgressBar hg;
    private ProgressBar st;

    private Image inventoryBg;

    public GameStage(Hero hero) {
        this.hero = hero;
        setDebugAll(true);
        mainTable = new Table() {{
            //setBounds(310, Values.SCREEN_HEIGHT - 200 + 75, 505, 100);
            setBounds(0, 0, Values.SCREEN_WIDTH, Values.SCREEN_HEIGHT);
            //setFillParent(true);
            setDebug(true);
        }};
        /*inventoryTable = new Table() {{
            setBounds(300, 330, 650+15, 65*4+20);
            setVisible(false);
            setDebug(true);
        }};*/

        //todo: add inv cells
        hp = new ProgressBar(0, 100, 1, false, UISpriteHolder.progressBarHeroHPStyle);
        hg = new ProgressBar(0, 100, 1, false, UISpriteHolder.progressBarHeroHGStyle);
        st = new ProgressBar(0, 100, 1, false, UISpriteHolder.progressBarHeroSTStyle);

        hp.setValue(100);
        hg.setValue(100);
        st.setValue(100);

        //inventoryTitle = new Label("Inventory", UISpriteHolder.labelStyle);
        //inventoryBg = new Image(UISpriteHolder.inventoryBg);

        mainTable.left().top().add(hp).padLeft(310).padTop(30).width(500).left().row();
        mainTable.add(hg).padLeft(310).padTop(12).width(500).left().row();
        mainTable.add(st).padLeft(310).width(500).padTop(12).left().row();
        //mainTable.add(inventoryBg).padTop(340).padLeft(315).width(650).height(400);

        //inventoryTable.left().top().add(inventoryTitle);

        addActor(mainTable);
        //addActor(inventoryTable);
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
        hero.keyTyped(character);
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean interfaceElementClicked = super.touchDown(screenX, screenY, pointer, button);
        if (interfaceElementClicked)
            return true;

        //todo:
        return false;
    }

    @Override
    public void act() {
        hp.setValue(hero.getHp());
        hg.setValue(hero.getHunger());
        st.setValue(hero.getStamina());

        super.act();
    }
}
