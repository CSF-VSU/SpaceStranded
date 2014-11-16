package ru.vsu.csf.twopeoplestudios.screens.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class UISpriteHolder {

    public static TextureRegion BUTTON;
    public static TextureRegion BUTTON_HOVERED;

    public UISpriteHolder() {
        BUTTON = new TextureRegion(new Texture(Gdx.files.internal("gfx/ui/button.png")));
        BUTTON_HOVERED = new TextureRegion(new Texture(Gdx.files.internal("gfx/ui/buttonHovered.png")));
    }
}
