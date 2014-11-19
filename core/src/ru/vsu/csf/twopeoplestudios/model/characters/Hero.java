package ru.vsu.csf.twopeoplestudios.model.characters;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class Hero {

    static final float VELOCITY = 3f;

    Vector2 position;
    Vector2 velocity;

    boolean leftPressed;
    boolean rightPressed;
    boolean upPressed;
    boolean downPressed;

    public Vector2 getPosition() {
        return position;
    }

    public Hero() {
        position = new Vector2(0, 0);
        velocity = new Vector2(0, 0);
        leftPressed = false;
        rightPressed = false;
        upPressed = false;
        downPressed = false;
    }

    public void update(float delta) {
        handleInputFlags();
        velocity.scl(delta).scl(VELOCITY);
        position.add(velocity);
    }

    private void handleInputFlags() {
        if (leftPressed && rightPressed) {
            leftPressed = rightPressed = false;
        }
        if (upPressed && downPressed) {
            upPressed = downPressed = false;
        }

        if (leftPressed)
            velocity.set(-1, velocity.y);
        if (rightPressed)
            velocity.set(1, velocity.y);
        if (upPressed)
            velocity.set(velocity.x, 1);
        if (downPressed)
            velocity.set(velocity.x, -1);
    }

    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                leftPressed = true;
                break;
            case Input.Keys.D:
                rightPressed = true;
                break;
            case Input.Keys.W:
                upPressed = true;
                break;
            case Input.Keys.S:
                downPressed = true;
                break;
        }
    }

    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                leftPressed = false;
                break;
            case Input.Keys.D:
                rightPressed = false;
                break;
            case Input.Keys.W:
                upPressed = false;
                break;
            case Input.Keys.S:
                downPressed = false;
                break;
        }
    }

    public void keyTyped(char character) {
        //...
    }
}
