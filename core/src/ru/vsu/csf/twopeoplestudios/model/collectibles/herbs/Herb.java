package ru.vsu.csf.twopeoplestudios.model.collectibles.herbs;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import ru.vsu.csf.twopeoplestudios.model.collectibles.Collectible;
import ru.vsu.csf.twopeoplestudios.model.contactListener.EntityTypes;
import ru.vsu.csf.twopeoplestudios.model.contactListener.collisionUserData.HerbUserData;

public class Herb extends Collectible {

    protected Vector2 position;
    protected Body body;

    public Herb(int id, Vector2 position, World world) {
        this.id = id;
        this.position = position;
        this.count = 1;

        BodyDef bodyDef = new BodyDef() {{
            type = BodyType.KinematicBody;
            position.set(getPosition().x + 0.5f, getPosition().y + 0.5f);
        }};
        body = world.createBody(bodyDef);

        final PolygonShape herbShape = new PolygonShape() {{
            setAsBox(0.5f, 0.5f);
        }};

        FixtureDef fixtureDef = new FixtureDef() {{
            shape = herbShape;
            filter.categoryBits = EntityTypes.HERB;
            filter.maskBits = EntityTypes.HERB_MASK;
            isSensor = true;
        }};
        body.createFixture(fixtureDef);
        body.setFixedRotation(true);
        body.setUserData(new HerbUserData(this));

        herbShape.dispose();
    }

    public int getId() {
        return id;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Body getBody() {
        return body;
    }
}
