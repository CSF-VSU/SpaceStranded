package ru.vsu.csf.twopeoplestudios;

import com.badlogic.gdx.Game;
import ru.vsu.csf.twopeoplestudios.screens.GameScreen;
import ru.vsu.csf.twopeoplestudios.screens.MainMenuScreen;
import ru.vsu.csf.twopeoplestudios.screens.MapTestScreen;

public class SpaceStranded extends Game {

    @Override
    public void create() {
        // Для того, чтобы посмотреть, как работает генератор карт, вам нужен этот экран:
        //setScreen(new MapTestScreen(this));

        // Экран главного меню:
        //setScreen(new MainMenuScreen(this));

        //Сразу игровой:
        setScreen(new GameScreen(this));
    }
}