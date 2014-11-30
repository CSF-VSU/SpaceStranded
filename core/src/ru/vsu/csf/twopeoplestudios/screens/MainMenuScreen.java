package ru.vsu.csf.twopeoplestudios.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.vsu.csf.twopeoplestudios.renderers.ui.UISpriteHolder;

public class MainMenuScreen extends AbstractScreen {

    private Stage stage;
    private Table table;

    private TextButton button;
    private TextButton exitBtn;
    private ProgressBar progressBar;

    private TextureRegion bg;

    float debug_progressKnobValue;

    public MainMenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        bg = new TextureRegion(new Texture(Gdx.files.internal("gfx/bg.jpg")));

        //Создаем:
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); //вот тут вот интересный момент: мы полностью отдаем управление stage.. а если и нам оно нужно?..

        table = new Table(UISpriteHolder.skinBtns) {{
            setBounds(50, 50, 500, 500);
        }}; // типа лэйаута

        debug_progressKnobValue = 0;
        progressBar = new ProgressBar(0, 100, 1, false, UISpriteHolder.progressBarStyle);


        button = new TextButton("Start new game", UISpriteHolder.textButtonStyle);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        exitBtn = new TextButton("Exit", UISpriteHolder.textButtonStyle);
        exitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        }); //тут, думаю, комментарии излишни


        //Собираем все вместе:
        table.add(button).height(90).padBottom(30).row(); //flow-interface для добавления элементов. PadBottom - отступ снизу для следующих компонентов.
        //Компоненты в таблице добавляются в ряд, переходить на следующий можно с помощью row()

        table.add(exitBtn).height(90).padBottom(30).row();
        table.add(progressBar);

        table.debug(); //эту и еще одну строку внизу...
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        debug_progressKnobValue += delta;
        if (debug_progressKnobValue > 1) {
            debug_progressKnobValue = 0;
        }
        progressBar.setValue(debug_progressKnobValue * 100f / 1f);

        cameraBatch.begin();
        cameraBatch.draw(bg, 0, 0);
        cameraBatch.end();

        stage.act(delta);
        stage.setDebugAll(true); //..можно раскомментить, тогда отобразится доп инфа о размерах, положении.. увидите.
        stage.draw();
    }

    @Override
    public void dispose() {
        //не забываем после себя подчищать код :3
        bg.getTexture().dispose();
        stage.dispose();

        super.dispose();
    }
}
