package ru.vsu.csf.twopeoplestudios.model.characters;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import ru.vsu.csf.twopeoplestudios.model.contactListener.EntityTypes;

public class Hero {

    static final float VELOCITY = 5f;

    Vector2 position;
    Vector2 velocity;

    boolean leftPressed;
    boolean rightPressed;
    boolean upPressed;
    boolean downPressed;

    World world;
    Body body;

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public Hero(World world) {
        position = new Vector2(0, 0);
        velocity = new Vector2(0, 0);
        leftPressed = false;
        rightPressed = false;
        upPressed = false;
        downPressed = false;

        this.world = world;
        createBody();
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef() {{
            type = BodyType.DynamicBody;
            position.set(0, 0);
        }};

        body = world.createBody(bodyDef);

        final PolygonShape polygonShape = new PolygonShape() {{
            setAsBox(0.5f, 0.5f);
        }};

        FixtureDef fixtureDef = new FixtureDef() {{
            shape = polygonShape;
            filter.categoryBits = EntityTypes.HERO;
            filter.maskBits = EntityTypes.HERO_MASK;
        }};

        body.createFixture(fixtureDef);
        body.setFixedRotation(true);

        polygonShape.dispose();
    }

    public void update(float delta) {
        handleInputFlags();
        velocity.scl(delta).scl(VELOCITY);
        body.setTransform(body.getPosition().add(velocity), 0);
    }

    private void handleInputFlags() {
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
