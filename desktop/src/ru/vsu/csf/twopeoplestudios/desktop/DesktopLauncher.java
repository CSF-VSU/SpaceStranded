package ru.vsu.csf.twopeoplestudios.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.vsu.csf.twopeoplestudios.SpaceStranded;
import ru.vsu.csf.twopeoplestudios.Values;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Values.SCREEN_WIDTH;
        config.height = Values.SCREEN_HEIGHT;
        config.title = "Space Stranded";
        new LwjglApplication(new SpaceStranded(), config);
    }
}
