package ru.vsu.csf.twopeoplestudios.screens.stages;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import ru.vsu.csf.twopeoplestudios.Values;
import ru.vsu.csf.twopeoplestudios.model.characters.Hero;
import ru.vsu.csf.twopeoplestudios.renderers.ui.UISpriteHolder;

public class GameStage extends Stage {

    private Hero hero;

    private Table table;

    private ProgressBar hp;
    private ProgressBar hg;
    private ProgressBar st;

    public GameStage(Hero hero) {
        this.hero = hero;
        //this.setDebugAll(true);
        table = new Table() {{
            setBounds(310, Values.SCREEN_HEIGHT - 200 + 75, 505, 100);
            //setDebug(true);
        }};

        hp = new ProgressBar(0, 100, 1, false, UISpriteHolder.progressBarHeroHPStyle);
        hg = new ProgressBar(0, 100, 1, false, UISpriteHolder.progressBarHeroHGStyle);
        st = new ProgressBar(0, 100, 1, false, UISpriteHolder.progressBarHeroSTStyle);

        hp.setValue(100);
        hg.setValue(100);
        st.setValue(100);

        table.left().top().padTop(10).add(hp).width(500).row();
        table.add(hg).padTop(12).width(500).row();
        table.add(st).width(500).padTop(12);
        addActor(table);
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
