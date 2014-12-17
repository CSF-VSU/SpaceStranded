package ru.vsu.csf.twopeoplestudios.model.weapons;

import com.badlogic.gdx.math.Vector2;
import ru.vsu.csf.twopeoplestudios.Settings;
import ru.vsu.csf.twopeoplestudios.Values;
import ru.vsu.csf.twopeoplestudios.model.characters.Hero;
import ru.vsu.csf.twopeoplestudios.model.collectibles.Collectible;
import ru.vsu.csf.twopeoplestudios.model.contactListener.EntityTypes;
import ru.vsu.csf.twopeoplestudios.model.map.Map;
import ru.vsu.csf.twopeoplestudios.renderers.MapRenderer;

public class RangedWeapon extends Collectible {

    public static final float PROJECTILE_SPEED = 0.5f;
    public static final float PROJECTILE_SPAWN_DISTANCE = 0.5f;

    private Hero hero;
    private Map map;

    public RangedWeapon(int id, Hero hero, Map map) {
        super(id, 1);
        this.hero = hero;
        this.map = map;
    }

    public void attack() {

        Vector2 position;

        if (Settings.SHOW_GRAPHICS) {
            position = new Vector2(Values.mousePos.x, Values.mousePos.y)
                    .sub(hero.getHeroPosition().x * MapRenderer.CELL_SIZE, hero.getHeroPosition().y * MapRenderer.CELL_SIZE);
        }
        else {
            position = new Vector2(Values.mousePos.x, Values.mousePos.y)
                    .sub(hero.getHeroPosition());
        }

        double rotation = Math.atan2(position.y, position.x);

        position.nor()
                .scl(PROJECTILE_SPAWN_DISTANCE)
                .add(hero.getHeroPosition());

        map.createProjectile(id, position, rotation, EntityTypes.HERO_ATK, EntityTypes.HERO_ATK_MASK);
    }
}
