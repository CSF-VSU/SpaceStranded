package ru.vsu.csf.twopeoplestudios.model.collectibles.herbs;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.HashSet;

public class HerbInfo extends Herb {
    private String name;
    private String description;
    private HashSet<HerbProperties> properties;


    public HerbInfo(int id, Vector2 position, World world) {
        super(id, position, world);
    }
}
