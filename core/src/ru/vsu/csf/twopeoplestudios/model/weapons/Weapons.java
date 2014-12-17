package ru.vsu.csf.twopeoplestudios.model.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public class Weapons {

    private static HashMap<Integer, ProjectileInfo> projectiles;
    private static HashMap<Integer, MeleeAttackType> meleeWeaponsCharacteristics;

    private Weapons() {
        meleeWeaponsCharacteristics = new HashMap<Integer, MeleeAttackType>() {{
            put(250, MeleeAttackType.SLASH_ATTACK);
        }};

        projectiles = new HashMap<Integer, ProjectileInfo>() {{
            put(200, new ProjectileInfo() {{
                textureRegion = new TextureRegion(new Texture(Gdx.files.internal("gfx/projectiles/blasterProjectile.png")));
                shape = new Vector2(0.1f, 0.1f);
            }});
        }};
    }

    private static Weapons instance;

    public static Weapons getInstance() {
        if (instance == null)
            instance = new Weapons();
        return instance;
    }

    public MeleeAttackType getMeleeAttackType(int id) {
        return meleeWeaponsCharacteristics.get(id);
    }

    public ProjectileInfo getProjectileInfo(int id) {
        return projectiles.get(id);
    }

    public TextureRegion getTextureRegion(int id) {
        return projectiles.get(id).textureRegion;
    }
}