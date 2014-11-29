package ru.vsu.csf.twopeoplestudios.model.characters;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import ru.vsu.csf.twopeoplestudios.model.collectibles.Inventory;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.Herb;
import ru.vsu.csf.twopeoplestudios.model.contactListener.EntityTypes;
import ru.vsu.csf.twopeoplestudios.model.contactListener.collisionUserData.HeroUserData;
import ru.vsu.csf.twopeoplestudios.model.map.Map;
import ru.vsu.csf.twopeoplestudios.screens.GameScreen;

public class Hero {

    static final float VELOCITY = 5f;
    static final float RUN_SPEED = 25f;

    //region Declarations
    Vector2 position;
    Vector2 velocity;

    boolean leftPressed;
    boolean rightPressed;
    boolean upPressed;
    boolean downPressed;

    World world;
    Map map;
    Body body;

    Inventory inventory;
    GameScreen gameScreen; //to call show/hide inventory
    boolean isShowingInventory;

    Herb herbUnderFeet;
    //endregion

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public Inventory getInventory() {
        return inventory;
    }

    //region Creating
    public Hero(World world, Map map) {
        position = new Vector2(0, 0);
        velocity = new Vector2(0, 0);
        leftPressed = false;
        rightPressed = false;
        upPressed = false;
        downPressed = false;

        this.world = world;
        this.map = map;
        createBody();

        herbUnderFeet = null;
        inventory = new Inventory();
        isShowingInventory = false;
    }

    public void initGameScreen(GameScreen screen) {
        this.gameScreen = screen;
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef() {{
            type = BodyType.DynamicBody; //динамическое = можно воздействовать на него силами или импульсами
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
        body.setFixedRotation(true); //запрещаем телу поворачиваться в процессе столновений или приложения сил и импульсов
        body.setUserData(new HeroUserData(this)); //юзер-дата, по которой из столкновения можно получить ссылку на героя

        polygonShape.dispose();
    }
    //endregion

    //region Update
    public void update(float delta) {
        handleInputFlags();

        Vector2 vel = body.getLinearVelocity();

        if (!leftPressed && !rightPressed) {
            body.setLinearVelocity(vel.x * 0.85f, vel.y);
        }
        if (!upPressed && !downPressed) {
            body.setLinearVelocity(vel.x, vel.y * 0.85f);
        }

        //величина импульса умножается на delta, чтобы персонаж двигаелся с одинаковой скоростью вне зависимости от фпс
        //todo: распределить импульсы так, чтобы движение по диагонали было с той же скоростью, что и по горизонтали/вертикали
        if (leftPressed && vel.x > -VELOCITY) {
            body.applyLinearImpulse(-RUN_SPEED * delta, 0, body.getPosition().x, body.getPosition().y, true);
            body.setTransform(body.getPosition().x, body.getPosition().y, 0);
        }
        if (rightPressed && vel.x < VELOCITY) {
            body.applyLinearImpulse(RUN_SPEED * delta, 0, body.getPosition().x, body.getPosition().y, true);
            body.setTransform(body.getPosition().x, body.getPosition().y, 0);
        }

        if (upPressed && vel.y < VELOCITY) {
            body.applyLinearImpulse(0, RUN_SPEED * delta, body.getPosition().x, body.getPosition().y, true);
            body.setTransform(body.getPosition().x, body.getPosition().y, 0);
        }
        if (downPressed && vel.y > -VELOCITY) {
            body.applyLinearImpulse(0, -RUN_SPEED * delta, body.getPosition().x, body.getPosition().y, true);
            body.setTransform(body.getPosition().x, body.getPosition().y, 0);
        }

        if (!(leftPressed || rightPressed || downPressed || upPressed)) {
            body.setLinearVelocity(vel.x * 0.91f, vel.y * 0.91f);
        }
    }
    //endregion

    //region Handling input
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
        switch (character) {
            case 'e':
            case 'у':
                if (herbUnderFeet != null)
                    collectHerb();
                break;
            case 'i':
            case 'ш':
                if (!isShowingInventory) {
                    isShowingInventory = true;
                    gameScreen.showInventory();
                } else {
                    isShowingInventory = false;
                    gameScreen.hideInventory();
                }
                break;
        }
    }
    //endregion

    public void onTouchHerb(Herb herb) {
        this.herbUnderFeet = herb;
    }

    public void onStopTouchingHerb() {
        this.herbUnderFeet = null;
    }

    private void collectHerb() {
        if (this.inventory.tryToPut(herbUnderFeet)) {
            map.destroyHerb(herbUnderFeet);
            herbUnderFeet = null;
        }
    }

}
