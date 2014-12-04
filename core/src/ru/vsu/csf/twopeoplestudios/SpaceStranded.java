package ru.vsu.csf.twopeoplestudios;

import com.badlogic.gdx.Game;
import ru.vsu.csf.twopeoplestudios.screens.GameScreen;
import ru.vsu.csf.twopeoplestudios.screens.MapTestScreen;
import ru.vsu.csf.twopeoplestudios.screens.TileDemoScreen;

public class SpaceStranded extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen(this));
    }
}