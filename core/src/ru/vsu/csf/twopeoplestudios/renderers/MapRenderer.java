package ru.vsu.csf.twopeoplestudios.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.Herb;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.HerbStorage;
import ru.vsu.csf.twopeoplestudios.model.contactListener.WorldContactListener;
import ru.vsu.csf.twopeoplestudios.model.map.Map;

public class MapRenderer {

    public Map map;
    HerbStorage herbStorage;

    private Box2DDebugRenderer debugRenderer;
    public OrthographicCamera camera;
    private World world;

    TextureRegion heroTexture;

    public MapRenderer() {
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(40, 32);
        camera.position.set(0, 0, 0);
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new WorldContactListener());

        herbStorage = HerbStorage.getInstance();
        map = new Map(world);

        heroTexture = new TextureRegion(new Texture(Gdx.files.internal("gfx/characters/hero.png")));
    }

    public void render(Batch batch, float delta) {
        debugRenderer.render(world, camera.combined);
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        map.update(delta);

        for (Herb h : map.herbs) {
            batch.draw(SpriteHolder.getTexture(h.getId()), h.getPosition().x, h.getPosition().y, 1, 1);
            //batch.draw(HerbStorage.getInstance().getTexture(h.getId()), h.getPosition().x, h.getPosition().y, 1, 1);
        }

        batch.draw(heroTexture, map.hero.getPosition().x - 0.5f, map.hero.getPosition().y - 0.5f, 1, 2);
        batch.end();
    }
}
