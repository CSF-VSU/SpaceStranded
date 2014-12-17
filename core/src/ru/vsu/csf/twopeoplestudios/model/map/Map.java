package ru.vsu.csf.twopeoplestudios.model.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import ru.vsu.csf.twopeoplestudios.Settings;
import ru.vsu.csf.twopeoplestudios.model.characters.Hero;
import ru.vsu.csf.twopeoplestudios.model.characters.monsters.Monster;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.Herb;
import ru.vsu.csf.twopeoplestudios.model.contactListener.EntityTypes;
import ru.vsu.csf.twopeoplestudios.model.weapons.FlyingProjectile;
import ru.vsu.csf.twopeoplestudios.model.weapons.ProjectileInfo;
import ru.vsu.csf.twopeoplestudios.model.weapons.RangedWeapon;
import ru.vsu.csf.twopeoplestudios.model.weapons.Weapons;
import ru.vsu.csf.twopeoplestudios.renderers.MapRenderer;

import java.util.LinkedList;

public class Map {

    World world;

    public LinkedList<Monster> monsters;
    public LinkedList<Herb> herbs;
    public LinkedList<Body> toDestroy;
    public LinkedList<FlyingProjectile> projectiles;

    public Hero hero;

    public Map(final World world) {
        this.world = world;
        hero = new Hero(world, this);
        herbs = new LinkedList<Herb>() {{
            if (Settings.SPAWN_HERBS)
                for (int i = 0; i < 20; i++)
                    add(new Herb(0, 1, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition()/*.scl(MapRenderer.CELL_SIZE)*/, world));
        }};
        monsters = new LinkedList<Monster>() {{
            if (Settings.SPAWN_MONSTERS)
                for (int i = 0; i < 20; i++)
                    add(new Monster(world, ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().getRandomPosition()));//.scl(MapRenderer.CELL_SIZE)));
        }};

        toDestroy = new LinkedList<Body>();
        projectiles = new LinkedList<FlyingProjectile>();
    }

    public void update(float delta) {
        for (FlyingProjectile f : projectiles) {
            Body b = f.body;
            b.setTransform((float) (b.getPosition().x + RangedWeapon.PROJECTILE_SPEED * Math.cos(b.getAngle())),
                    (float) (b.getPosition().y + RangedWeapon.PROJECTILE_SPEED * Math.sin(b.getAngle())),
                    b.getAngle());
        }

        for (Body b : toDestroy) {
            world.destroyBody(b);
        }
        toDestroy.clear();

        hero.update(delta);
        for (Monster m : monsters)
            m.update(delta);
    }

    public void destroyHerb(Herb herb) {
        toDestroy.add(herb.getBody());
        herbs.remove(herbs.indexOf(herb));
    }

    public void createProjectile(final int proj_id, final Vector2 pos, double rotation, final short category, final short mask) {
        final ProjectileInfo projectileInfo = Weapons.getInstance().getProjectileInfo(proj_id);

        final Body b = world.createBody(new BodyDef() {{
            position.set(pos);
            type = BodyType.KinematicBody;
        }});

        FlyingProjectile newProjectile = new FlyingProjectile() {{
            id = proj_id;
            body = b;
        }};
        projectiles.add(newProjectile);
        b.setUserData(newProjectile);

        b.setBullet(true);
        b.setTransform(pos, (float) rotation);

        final PolygonShape polygonShape = new PolygonShape() {{
            setAsBox(projectileInfo.shape.x, projectileInfo.shape.y);
        }};
        b.createFixture(new FixtureDef() {{
            shape = polygonShape;
            isSensor = true;
            filter.categoryBits = category;
            filter.maskBits = mask;
        }});
        polygonShape.dispose();
    }

    public void unleashHellAtNight() {
        Gdx.app.log("Timing", "Spawning mobs, scary as fuck.");
    }
}
