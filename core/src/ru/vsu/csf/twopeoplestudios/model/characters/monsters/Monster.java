package ru.vsu.csf.twopeoplestudios.model.characters.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import ru.vsu.csf.twopeoplestudios.model.characters.Character;
import ru.vsu.csf.twopeoplestudios.model.characters.monsters.behaviour.BehaviourState;
import ru.vsu.csf.twopeoplestudios.model.contactListener.EntityTypes;
import ru.vsu.csf.twopeoplestudios.model.weapons.Facing;
import ru.vsu.csf.twopeoplestudios.renderers.MapRenderer;

public class Monster extends Character {

    //todo: 5. Monster consts
    private static final float RESTITUTION = 0.8f;
    private static final float FRICTION = 0.5f;
    private static final float DENSITY = 0.8f;
    private static final float RUN_SPEED = 45000;

    private BehaviourState state;

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public Monster(World world, Vector2 spawnPos) {
        this.world = world;
        this.charPosition = spawnPos;
        this.state = BehaviourState.WANDER;

        createBody();
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef() {{
            position.set(charPosition);
            type = BodyType.DynamicBody;
        }};

        final PolygonShape polygonShape = new PolygonShape() {{
            setAsBox(MapRenderer.CELL_SIZE/2f, MapRenderer.CELL_SIZE/2f);
        }};

        FixtureDef fixtureDef = new FixtureDef() {{
            shape = polygonShape;
//            restitution = RESTITUTION;
//            friction = FRICTION;
//            density = DENSITY;
            filter.categoryBits = EntityTypes.ENEMY;
            filter.maskBits = EntityTypes.ENEMY_MASK;
        }};

        this.body = world.createBody(bodyDef);
        this.body.createFixture(fixtureDef);
        this.body.setFixedRotation(true);
        this.body.setUserData(this);

        polygonShape.dispose();
    }

    public void update(float delta) {
        switch (state) {
            case WANDER:

                break;
        }
        //moveIntoChosenDirection(Facing.DOWN_LEFT);
        moveToPoint(new Vector2(64 * MapRenderer.CELL_SIZE, 32 * MapRenderer.CELL_SIZE));
    }

    public void beAttacked() {
        Gdx.app.log("", "Ouch!");
    }

    public void moveIntoChosenDirection (Facing direction) {
        switch (direction) {
            case UP:
                this.body.applyLinearImpulse(0, RUN_SPEED, body.getPosition().x, body.getPosition().y, true );
                this.facing = Facing.UP;
                break;
            case DOWN:
                this.body.applyLinearImpulse(0, -RUN_SPEED, body.getPosition().x, body.getPosition().y, true );
                this.facing = Facing.DOWN;
                break;
            case LEFT:
                this.body.applyLinearImpulse(-RUN_SPEED, 0, body.getPosition().x, body.getPosition().y, true );
                this.facing = Facing.LEFT;
                break;
            case RIGHT:
                this.body.applyLinearImpulse(RUN_SPEED, 0, body.getPosition().x, body.getPosition().y, true );
                this.facing = Facing.RIGHT;
                break;
            case UP_RIGHT:
                this.body.applyLinearImpulse(RUN_SPEED, RUN_SPEED, body.getPosition().x, body.getPosition().y, true );
                this.facing = Facing.UP_RIGHT;
                break;
            case UP_LEFT:
                this.body.applyLinearImpulse(-RUN_SPEED, RUN_SPEED, body.getPosition().x, body.getPosition().y, true );
                this.facing = Facing.UP_LEFT;
                break;
            case DOWN_LEFT:
                this.body.applyLinearImpulse(-RUN_SPEED, -RUN_SPEED, body.getPosition().x, body.getPosition().y, true );
                this.facing = Facing.DOWN_LEFT;
                break;
            case DOWN_RIGHT:
                this.body.applyLinearImpulse(RUN_SPEED, -RUN_SPEED, body.getPosition().x, body.getPosition().y, true );
                this.facing = Facing.DOWN_RIGHT;
                break;
            default:
                this.body.applyLinearImpulse(0, RUN_SPEED, body.getPosition().x, body.getPosition().y, true );
                this.facing = Facing.UP;
                break;
        }
    }

    public void moveToPoint (Vector2 point) {
        float curX = this.body.getPosition().x;
        float curY = this.body.getPosition().y;
        float deltaX = point.x - curX;
        float deltaY = point.y - curY;
        float tg = (deltaY) / (deltaX);
        if (deltaX > 0)
            this.body.applyLinearImpulse(RUN_SPEED, tg * RUN_SPEED, curX, curY, true);
        else
            this.body.applyLinearImpulse(-RUN_SPEED, -tg * RUN_SPEED, curX, curY, true);


    }
}
