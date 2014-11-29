package ru.vsu.csf.twopeoplestudios;

import com.badlogic.gdx.Game;
import ru.vsu.csf.twopeoplestudios.screens.GameScreen;

public class SpaceStranded extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen(this));
    }
}