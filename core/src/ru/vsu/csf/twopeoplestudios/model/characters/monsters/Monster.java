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
import ru.vsu.csf.twopeoplestudios.renderers.MapRenderer;

public class Monster extends Character {

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
            restitution = 0.8f;
            friction = 0.5f;
            density = 0.8f;
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
    }

    public void beAttacked() {
        Gdx.app.log("", "Ouch!");
    }
}
