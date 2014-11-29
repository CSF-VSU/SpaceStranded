package ru.vsu.csf.twopeoplestudios.renderers.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Button {

    private static float BTN_PADDING_LEFT = 5;
    private static float BTN_PADDING_TOP = 5;

    String caption;
    PressAction action;

    Sprite btnImage;
    Sprite btnImageHovered;

    public Button(String caption) {
        this.caption = caption;

        /*this.btnImage = new Sprite(UISpriteHolder.BUTTON) {{
            setPosition(0, 0);
        }};
        this.btnImageHovered = new Sprite(UISpriteHolder.BUTTON_HOVERED) {{
            setPosition(0, 0);
        }};*/
    }

    public Button setPosition(float x, float y) {
        this.btnImage.setPosition(x, y);
        this.btnImageHovered.setPosition(x, y);
        return this;
    }

    public Button setOnClickAction(PressAction action) {
        this.action = action;
        return this;
    }

    public void press() {
        action.perform();
    }

    public boolean isHovered(float mouseX, float mouseY) {
        return btnImage.getBoundingRectangle().contains(mouseX, mouseY);
    }

    public void render(Batch batch, float mouseX, float mouseY) {
        if (!isHovered(mouseX, mouseY))
            btnImage.draw(batch);
        else
            btnImageHovered.draw(batch);

        //UISpriteHolder.regularFont.draw(batch, caption, btnImage.getX() + BTN_PADDING_LEFT, btnImage.getY() + BTN_PADDING_TOP);
    }
}