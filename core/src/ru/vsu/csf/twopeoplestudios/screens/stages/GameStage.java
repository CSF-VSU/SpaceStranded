package ru.vsu.csf.twopeoplestudios.screens.stages;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.vsu.csf.twopeoplestudios.model.characters.Hero;

public class GameStage extends Stage {

    private Hero hero;

    public GameStage(Hero hero) {
        this.hero = hero;
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
}
