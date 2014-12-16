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

    //todo: 5. Monster consts
    private static final float RESTITUTION = 0.8f;
    private static final float FRICTION = 0.5f;
    private static final float DENSITY = 0.8f;

    private static final float VISIBLE_BREADTH = 100;
    private static final float VISIBLE_DISTANCE = 240;
    private static final float SENSOR_SHIFT = VISIBLE_DISTANCE - 50;

    private BehaviourState state;

    private MonsterHeroVision monsterHeroVision;

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public Monster(World world, Vector2 spawnPos) {
        this.world = world;
        this.charPosition = spawnPos;
        this.state = BehaviourState.WANDER;
        this.facing = Facing.DOWN_LEFT;

        createBody();
        createVision();
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

                break;
        }
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
        Gdx.app.log("Monster", "I see him!");
    }

    public void stopSeeHero() {
        Gdx.app.log("Monster", "I lost him!");
    }
}
