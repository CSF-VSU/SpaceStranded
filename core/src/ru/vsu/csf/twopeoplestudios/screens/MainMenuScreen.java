package ru.vsu.csf.twopeoplestudios.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.vsu.csf.twopeoplestudios.renderers.ui.UISpriteHolder;

public class MainMenuScreen extends AbstractScreen {

    private Stage stage;
    private Table table;

    private TextButton button;
    private TextButton exitBtn;

    private BitmapFont bitmapFont;

    //todo: изучить остальные компоненты!
    /*private Label title;
    private ProgressBar progressBar;*/

    private TextureRegion bg;

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

        table = new Table(UISpriteHolder.skin) {{
            setBounds(50, 50, 500, 500);
        }}; // типа лэйаута

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

        table.add(exitBtn).height(90);
        //table.debug(); //эту и еще одну строку внизу...
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        batch.draw(bg, 0, 0);
        batch.end();

        stage.act(delta);
        //stage.setDebugAll(true); //..можно раскомментить, тогда отобразится доп инфа о размерах, положении.. увидите.
        stage.draw();
    }

    @Override
    public void dispose() {
        //не забываем после себя подчищать код :3
        bitmapFont.dispose();
        bg.getTexture().dispose();
        stage.dispose();

        super.dispose();
    }
}
