package ru.vsu.csf.twopeoplestudios.model.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import ru.vsu.csf.twopeoplestudios.model.contactListener.EntityTypes;

import java.util.HashMap;

public class HeroAttacks {

    public static HashMap<MeleeAttackType, HashMap<Facing, Body>> attacks;

    public static void add(final World world, MeleeAttackType attackType, final Facing facing, final Vector2[] polygon) {
        final BodyDef bodyDef = new BodyDef() {{
            type = BodyType.KinematicBody;
            position.set(0, 0);
        }
        };
        final PolygonShape polygonShape = new PolygonShape() {{
            set(polygon);
        }
        };
        final FixtureDef fixtureDef = new FixtureDef() {{
            shape = polygonShape;
            filter.categoryBits = EntityTypes.HERO_ATK;
            filter.maskBits = EntityTypes.HERO_ATK_MASK;
            isSensor = true;
        }
        };

        final Body b = world.createBody(bodyDef);
        b.createFixture(fixtureDef);
        b.setActive(false);

        if (!attacks.containsKey(attackType))
            attacks.put(attackType, new HashMap<Facing, Body>() {
                {
                    put(facing, b);
                }
            });
        else {
            HashMap<Facing, Body> data = attacks.get(attackType);
            data.put(facing, b);
        }

        polygonShape.dispose();
    }

    public static void init(final World world) {

        attacks = new HashMap<MeleeAttackType, HashMap<Facing, Body>>();

        add(world, MeleeAttackType.SLASH_ATTACK, Facing.DOWN, new Vector2[]{
                new Vector2(45,0),
                new Vector2(30, -45),
                new Vector2(0, -70),
                new Vector2(-30, -45),
                new Vector2(-45, 0)
        });
        add(world, MeleeAttackType.SLASH_ATTACK, Facing.UP, new Vector2[]{ new Vector2(-40, 0), new Vector2(40,0), new Vector2(0,  60) });
        add(world, MeleeAttackType.SLASH_ATTACK, Facing.LEFT,  new Vector2[]{ new Vector2(0, 40), new Vector2(0, -40), new Vector2(60, 0) });
        add(world, MeleeAttackType.SLASH_ATTACK, Facing.RIGHT,  new Vector2[]{ new Vector2(0, 40), new Vector2(0, -40), new Vector2(-60, 0) });
//        add(world, MeleeAttackType.SLASH_ATTACK, Facing.DOWN, new Vector2[]{ new Vector2(-40, 0), new Vector2(40,0), new Vector2(0, -40) });
//        add(world, MeleeAttackType.SLASH_ATTACK, Facing.DOWN, new Vector2[]{ new Vector2(-40, 0), new Vector2(40,0), new Vector2(0, -40) });
//        add(world, MeleeAttackType.SLASH_ATTACK, Facing.DOWN, new Vector2[]{ new Vector2(-40, 0), new Vector2(40,0), new Vector2(0, -40) });
//        add(world, MeleeAttackType.SLASH_ATTACK, Facing.DOWN, new Vector2[]{ new Vector2(-40, 0), new Vector2(40,0), new Vector2(0, -40) });
    }
}
