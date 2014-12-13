package ru.vsu.csf.twopeoplestudios.model.collectibles.herbs;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import ru.vsu.csf.twopeoplestudios.model.collectibles.Collectible;
import ru.vsu.csf.twopeoplestudios.model.contactListener.EntityTypes;
import ru.vsu.csf.twopeoplestudios.model.contactListener.collisionUserData.HerbUserData;
import ru.vsu.csf.twopeoplestudios.renderers.MapRenderer;

public class Herb extends Collectible {

    protected Vector2 position;
    protected Body body;

    public Herb(int id, int count, Vector2 position, World world) {
        super(id, count);

        this.position = position;

        BodyDef bodyDef = new BodyDef() {{
            type = BodyType.KinematicBody;
            position.set(getPosition().x + MapRenderer.CELL_SIZE/2f, getPosition().y + MapRenderer.CELL_SIZE/2f);
        }};
        body = world.createBody(bodyDef);

        final PolygonShape herbShape = new PolygonShape() {{
            setAsBox(MapRenderer.CELL_SIZE/2f, MapRenderer.CELL_SIZE/2f);
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
