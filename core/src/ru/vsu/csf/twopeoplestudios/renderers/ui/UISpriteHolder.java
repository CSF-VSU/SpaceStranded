package ru.vsu.csf.twopeoplestudios.renderers.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class UISpriteHolder {

    public static BitmapFont font32;

    public static TextureAtlas atlas;
    public static Skin skin;

    public static TextButton.TextButtonStyle textButtonStyle;

    public UISpriteHolder() {
        atlas = new TextureAtlas(Gdx.files.internal("gfx/ui/button.pack")); //класс для загрузки текстурных паков - здеся атласы
        skin = new Skin(atlas); //считай, стиль. Применяется к разным UI-элементам

        font32 = new BitmapFont(Gdx.files.internal("gfx/fonts/main32.fnt"), false); //подгрузка готового шрифта из файла

        textButtonStyle = new TextButton.TextButtonStyle() {{ //стиль, применяющийся для создаваемых кнопок
            up = skin.getDrawable("button.normal"); //сами строки эти можно подсмотреть в *.pack-файле
            down = skin.getDrawable("button.pressed");
            pressedOffsetX = 2; //величина эффекта вдавливания кнопки при нажатии
            pressedOffsetY = -2;
            font = font32;
        }};
    }
}
