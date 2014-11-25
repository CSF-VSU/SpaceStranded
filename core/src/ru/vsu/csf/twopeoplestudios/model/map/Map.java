package ru.vsu.csf.twopeoplestudios.model.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import ru.vsu.csf.twopeoplestudios.model.characters.Hero;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.Herb;

import java.util.ArrayList;

public class Map {

    World world;

    public ArrayList<Herb> herbs;
    public Hero hero;

    public Map(World world) {
        this.world = world;
        hero = new Hero(world);
        herbs = new ArrayList<Herb>() {{
            add(new Herb(0, new Vector2(4, 4)));
            add(new Herb(0, new Vector2(-2, -2)));
        }};
    }

    public void update(Batch batch, float delta) {
        /*for (Herb h : herbs) {
            HerbStorage.getInstance().draw(batch, h.getId());
        }*/
        hero.update(delta);
    }
}
