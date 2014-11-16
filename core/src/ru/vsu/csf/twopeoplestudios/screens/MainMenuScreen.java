package ru.vsu.csf.twopeoplestudios.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import ru.vsu.csf.twopeoplestudios.screens.ui.Button;
import ru.vsu.csf.twopeoplestudios.screens.ui.LinearLayout;

public class MainMenuScreen extends AbstractScreen {

    Vector2 mousePos = new Vector2(0, 0);
    LinearLayout menu;

    public MainMenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                mousePos = new Vector2(screenX, screenY);
                return true;
            }
        });

        menu = new LinearLayout().setMarginLeft(50).setMarginTop(200).setPadding(20);
        menu.addButton(new Button("Новая игра")).addButton(new Button("Два")).addButton(new Button("Выход"));
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        menu.render(batch, mousePos.x, mousePos.y);
        batch.end();
    }
}
