package ru.vsu.csf.twopeoplestudios.model.characters.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import ru.vsu.csf.twopeoplestudios.Values;
import ru.vsu.csf.twopeoplestudios.model.characters.Character;
import ru.vsu.csf.twopeoplestudios.model.characters.monsters.behaviour.BehaviourState;
import ru.vsu.csf.twopeoplestudios.model.characters.monsters.sensors.MonsterHeroVision;
import ru.vsu.csf.twopeoplestudios.model.contactListener.EntityTypes;
import ru.vsu.csf.twopeoplestudios.model.weapons.Facing;
import ru.vsu.csf.twopeoplestudios.renderers.MapRenderer;

public class Monster extends Character {

    private static final float RESTITUTION = 0.8f;
    private static final float FRICTION = 0.5f;
    private static final float DENSITY = 0.8f;

    private static final float RUN_SPEED = 3f;

    private static final float VISIBLE_BREADTH = 2;
    private static final float VISIBLE_DISTANCE = 4;
    private static final float SENSOR_SHIFT = VISIBLE_DISTANCE - 0.5f;
    private static final float TG_PI_8 = (float) Math.tan(Math.PI / 8);
    private static final float TG_3_PI_8 = (float) Math.tan(3 * Math.PI / 8);

    private BehaviourState state;

    private MonsterHeroVision monsterHeroVision;

    private boolean seesHero;
    private Vector2 heroPosition;


    public Vector2 getPosition() {
        return body.getPosition();
    }

    public Monster(World world, Vector2 spawnPos) {
        this.world = world;
        this.charPosition = spawnPos;
        this.state = BehaviourState.WANDER;
        this.facing = Facing.DOWN_LEFT;
        this.seesHero = false;

        createBody();
        createVision();
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef() {{
            position.set(charPosition);
            type = BodyType.DynamicBody;
        }};

        final PolygonShape polygonShape = new PolygonShape() {{
            //setAsBox(MapRenderer.CELL_SIZE/2f, MapRenderer.CELL_SIZE/2f);
            setAsBox(0.5f, 0.5f);
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

    private void createVision() {
        monsterHeroVision = new MonsterHeroVision() {{
            sensor = world.createBody(new BodyDef() {{
                type = BodyType.DynamicBody;

                switch (facing) {
                    case DOWN:
                        position.set(body.getPosition().x, body.getPosition().y - SENSOR_SHIFT);
                        break;
                    case UP:
                        position.set(body.getPosition().x, body.getPosition().y + SENSOR_SHIFT);
                        break;
                    case RIGHT:
                        position.set(body.getPosition().x + SENSOR_SHIFT, body.getPosition().y);
                        break;
                    case LEFT:
                        position.set(body.getPosition().x - SENSOR_SHIFT, body.getPosition().y);
                        break;

                    case UP_RIGHT:
                        position.set(body.getPosition().x + SENSOR_SHIFT * Values.SQRT_2_DIV_2, body.getPosition().y + SENSOR_SHIFT * Values.SQRT_2_DIV_2);
                        break;
                    case DOWN_LEFT:
                        position.set(body.getPosition().x - SENSOR_SHIFT * Values.SQRT_2_DIV_2, body.getPosition().y - SENSOR_SHIFT * Values.SQRT_2_DIV_2);
                        break;
                    case UP_LEFT:
                        position.set(body.getPosition().x - SENSOR_SHIFT * Values.SQRT_2_DIV_2, body.getPosition().y + SENSOR_SHIFT * Values.SQRT_2_DIV_2);
                        break;
                    case DOWN_RIGHT:
                        position.set(body.getPosition().x + SENSOR_SHIFT * Values.SQRT_2_DIV_2, body.getPosition().y - SENSOR_SHIFT * Values.SQRT_2_DIV_2);
                        break;
                }
            }});
        }};
        monsterHeroVision.parent = this;

        final PolygonShape polygonShape = new PolygonShape() {{
            setAsBox(VISIBLE_BREADTH, VISIBLE_DISTANCE);
        }};

        monsterHeroVision.sensor.createFixture(new FixtureDef() {
            {
                shape = polygonShape;
                isSensor = true;
                filter.categoryBits = EntityTypes.ENEMY_SIGHT;
                filter.maskBits = EntityTypes.ENEMY_SIGHT_MASK;
            }
        });
        polygonShape.dispose();

        monsterHeroVision.sensor.setUserData(this.monsterHeroVision);
    }

    public void update(float delta) {
        updateSensors();

        switch (state) {
            case WANDER:
                if (seesHero)
                    moveToPoint(heroPosition);
                else
                    body.setLinearVelocity(0, 0);
                break;
        }
        //moveIntoChosenDirection(Facing.DOWN_LEFT);
        //moveToPoint(new Vector2(10, 10));
    }

    private void updateSensors() {
        switch (facing) {
            case DOWN:
                monsterHeroVision.sensor.setTransform(body.getPosition().x, body.getPosition().y - SENSOR_SHIFT, 0);
                break;
            case UP:
                monsterHeroVision.sensor.setTransform(body.getPosition().x, body.getPosition().y + SENSOR_SHIFT, 0);
                break;
            case RIGHT:
                monsterHeroVision.sensor.setTransform(body.getPosition().x + SENSOR_SHIFT, body.getPosition().y, Values.PI_DIV_2);
                break;
            case LEFT:
                monsterHeroVision.sensor.setTransform(body.getPosition().x - SENSOR_SHIFT, body.getPosition().y, - Values.PI_DIV_2);
                break;

            case UP_RIGHT:
                monsterHeroVision.sensor.setTransform(
                        body.getPosition().x + SENSOR_SHIFT * Values.SQRT_2_DIV_2,
                        body.getPosition().y + SENSOR_SHIFT * Values.SQRT_2_DIV_2,
                        - Values.PI_DIV_4);
                break;
            case DOWN_LEFT:
                monsterHeroVision.sensor.setTransform(
                        body.getPosition().x - SENSOR_SHIFT * Values.SQRT_2_DIV_2,
                        body.getPosition().y - SENSOR_SHIFT * Values.SQRT_2_DIV_2,
                        3 * Values.PI_DIV_4);
                break;
            case UP_LEFT:
                monsterHeroVision.sensor.setTransform(
                        body.getPosition().x - SENSOR_SHIFT * Values.SQRT_2_DIV_2,
                        body.getPosition().y + SENSOR_SHIFT * Values.SQRT_2_DIV_2,
                        Values.PI_DIV_4);
                break;
            case DOWN_RIGHT:
                monsterHeroVision.sensor.setTransform(
                        body.getPosition().x + SENSOR_SHIFT * Values.SQRT_2_DIV_2,
                        body.getPosition().y - SENSOR_SHIFT * Values.SQRT_2_DIV_2,
                        -3 * Values.PI_DIV_4);
                break;
        }
    }

    public void beAttacked() {
        Gdx.app.log("", "Ouch!");
    }

    public void seeHero(Vector2 heroPosition) {
        this.heroPosition = heroPosition;
        seesHero = true;
    }

    public void stopSeeHero() {
        seesHero = false;
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

        Vector2 delta = point.sub(body.getPosition());
        if (delta.len() < 0.3f) {
            body.setLinearVelocity(0, 0);
//            Gdx.app.log("", "Job done");
            return;
        }

        if (delta.len() > RUN_SPEED)
            delta.scl(RUN_SPEED / delta.len());

        //body.applyForce(delta, body.getPosition(), true);
        //body.setLinearVelocity(body.getLinearVelocity().nor().scl(RUN_SPEED));

        body.setLinearVelocity(delta.x, delta.y);
        if (delta.x > 0)
            if (delta.y > 0) {
                if (delta.y / delta.x < TG_PI_8)
                    this.facing = Facing.RIGHT;
                else if (delta.y / delta.x < TG_3_PI_8)
                    this.facing = Facing.UP_RIGHT;
                else
                    this.facing = Facing.UP;
            }
            else {
                if (delta.y / delta.x > -TG_PI_8)
                    this.facing = Facing.RIGHT;
                else if (delta.y / delta.x > -TG_3_PI_8)
                    this.facing = Facing.DOWN_RIGHT;
                else
                    this.facing = Facing.DOWN;
            }
        else if (delta.y > 0) {
            if (delta.y / delta.x > -TG_PI_8)
                this.facing = Facing.LEFT;
            else if (delta.y / delta.x > -TG_3_PI_8)
                this.facing = Facing.UP_LEFT;
            else
                this.facing = Facing.UP;
        }
        else {
            if (delta.y / delta.x < TG_PI_8)
                this.facing = Facing.LEFT;
            else if (delta.y / delta.x < TG_3_PI_8)
                this.facing = Facing.DOWN_LEFT;
            else
                this.facing = Facing.DOWN;
        }
    }
}