package ru.vsu.csf.twopeoplestudios.renderers.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.vsu.csf.twopeoplestudios.Values;

import java.util.ArrayList;

public class LinearLayout {

    private static int BUTTON_WIDTH;
    private static int BUTTON_HEIGHT;

    ArrayList<Button> buttons;
    Orientation orientation;

    int marginTop;
    int marginLeft;

    int padding;

    public LinearLayout() {
        buttons = new ArrayList<Button>();
        orientation = Orientation.VERTICAL;
        //BUTTON_WIDTH = UISpriteHolder.BUTTON.getRegionWidth();
        //BUTTON_HEIGHT = UISpriteHolder.BUTTON.getRegionHeight();
    }

    public LinearLayout(Orientation orientation) {
        this();
        this.orientation = orientation;
    }


    public LinearLayout setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
        return this;
    }

    public LinearLayout setMarginTop(int marginTop) {
        this.marginTop = marginTop;
        return this;
    }

    public LinearLayout setPadding(int padding) {
        this.padding = padding;
        return this;
    }


    public LinearLayout addButton(Button button) {
        switch (orientation) {
            case VERTICAL:
                button.setPosition(marginLeft, Values.SCREEN_HEIGHT - marginTop - (BUTTON_HEIGHT + padding) * buttons.size());
                break;
            case HORIZONTAL:
                button.setPosition(marginLeft + (BUTTON_WIDTH + padding) * buttons.size(), Values.SCREEN_HEIGHT - marginTop);
                break;
        }
        buttons.add(button);
        return this;
    }

    public void render(Batch batch, float mouseX, float mouseY) {
        for (Button b : buttons) {
            b.render(batch, mouseX, mouseY);
        }
    }
}
