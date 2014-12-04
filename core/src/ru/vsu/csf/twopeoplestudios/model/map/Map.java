package ru.vsu.csf.twopeoplestudios.model.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import ru.vsu.csf.twopeoplestudios.model.characters.Hero;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.Herb;

import java.util.LinkedList;

public class Map {

    World world;

    public LinkedList<Herb> herbs;
    public LinkedList<Body> toDestroy;

    public Hero hero;

    public Map(final World world) {
        this.world = world;
        hero = new Hero(world, this);
        herbs = new LinkedList<Herb>() {{
            add(new Herb(0, 1, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition(), world));
            add(new Herb(0, 1, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition(), world));
            add(new Herb(0, 1, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition(), world));
            add(new Herb(0, 1, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition(), world));
            add(new Herb(0, 1, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition(), world));
            add(new Herb(0, 1, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition(), world));
            add(new Herb(1, 1, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition(), world));
            add(new Herb(1, 1, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition(), world));
            add(new Herb(1, 1, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition(), world));
            add(new Herb(1, 1, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition(), world));
            add(new Herb(1, 1, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition(), world));
            add(new Herb(1, 1, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition(), world));
            add(new Herb(1, 1, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition(), world));
            add(new Herb(2, 1, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition(), world));
            add(new Herb(2, 1, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition(), world));
            add(new Herb(2, 1, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition(), world));
            add(new Herb(2, 1, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition(), world));
            add(new Herb(2, 1, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition(), world));
            add(new Herb(2, 1, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition(), world));
        }};
        toDestroy = new LinkedList<Body>();
    }

    public void update(float delta) {
        for (Body b : toDestroy) {
            world.destroyBody(b);
        }
        toDestroy.clear();

        hero.update(delta);
    }

    public void destroyHerb(Herb herb) {
        toDestroy.add(herb.getBody());
        herbs.remove(herbs.indexOf(herb));
    }
}
