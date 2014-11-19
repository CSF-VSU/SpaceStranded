package ru.vsu.csf.twopeoplestudios.renderers.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class UISpriteHolder {

    public static TextureRegion BUTTON;
    public static TextureRegion BUTTON_HOVERED;

    public static BitmapFont regularFont;
    public static BitmapFont titleFont;

    public UISpriteHolder() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("gfx/fonts/picaresque.ttf"));
        regularFont = generator.generateFont(new FreeTypeFontGenerator.FreeTypeFontParameter() {{
            size = 12;
        }}); // font size 12 pixels
        regularFont.setColor(Color.BLUE);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("gfx/fonts/postmodernTitles.ttf"));
        titleFont = generator.generateFont(20);
        titleFont.setColor(Color.BLUE);

        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        BUTTON = new TextureRegion(new Texture(Gdx.files.internal("gfx/ui/button.png")));
        BUTTON_HOVERED = new TextureRegion(new Texture(Gdx.files.internal("gfx/ui/buttonHovered.png")));
    }
}
