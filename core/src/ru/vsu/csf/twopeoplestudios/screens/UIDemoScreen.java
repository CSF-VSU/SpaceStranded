package ru.vsu.csf.twopeoplestudios.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class UIDemoScreen extends AbstractScreen {

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;

    private TextButton button;
    private TextButton exitBtn;

    private BitmapFont bitmapFont;

    //todo: изучить остальные компоненты!
    private Label title;
    private ProgressBar progressBar;

    private TextureRegion bg;

    public UIDemoScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        bg = new TextureRegion(new Texture(Gdx.files.internal("gfx/bg.jpg")));

        //Создаем:
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); //вот тут вот интересный момент: мы полностью отдаем управление stage.. а если и нам оно нужно?..

        atlas = new TextureAtlas(Gdx.files.internal("gfx/ui/button.pack")); //класс для загрузки текстурных паков - здеся атласы
        skin = new Skin(atlas); //считай, стиль. Применяется к разным UI-элементам
        table = new Table(skin) {{
            setBounds(50, 50, 500, 500);
        }}; // типа лэйаута
        bitmapFont = new BitmapFont(Gdx.files.internal("gfx/fonts/main32.fnt"), false); //подгрузка готового шрифта из файла

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle() {{ //стиль, применяющийся для создаваемых кнопок
            up = skin.getDrawable("button.normal"); //сами строки эти можно подсмотреть в *.pack-файле
            down = skin.getDrawable("button.pressed");
            pressedOffsetX = 2; //величина эффекта вдавливания кнопки при нажатии
            pressedOffsetY = -2;
            font = bitmapFont;
        }};

        button = new TextButton("Start new game", textButtonStyle);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        exitBtn = new TextButton("Exit", textButtonStyle);
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
        skin.dispose();
        atlas.dispose();
        bitmapFont.dispose();
        bg.getTexture().dispose();
        stage.dispose();

        super.dispose();
    }
}
