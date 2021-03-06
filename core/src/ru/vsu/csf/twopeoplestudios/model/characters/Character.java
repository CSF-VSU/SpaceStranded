package ru.vsu.csf.twopeoplestudios.model.characters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import ru.vsu.csf.twopeoplestudios.model.weapons.Facing;

public abstract class Character {

    protected World world;

    protected Vector2 charPosition;
    protected Body body;

    protected float maxHp;
    protected float hp;

    protected Facing facing;

    public Facing getFacing() {
        return facing;
    }
}
