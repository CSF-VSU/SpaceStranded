package ru.vsu.csf.twopeoplestudios.model.contactListener;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import ru.vsu.csf.twopeoplestudios.model.characters.Hero;
import ru.vsu.csf.twopeoplestudios.model.characters.monsters.Monster;
import ru.vsu.csf.twopeoplestudios.model.characters.monsters.sensors.MonsterHeroVision;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.Herb;
import ru.vsu.csf.twopeoplestudios.model.contactListener.collisionUserData.HerbUserData;
import ru.vsu.csf.twopeoplestudios.model.contactListener.collisionUserData.HeroUserData;
import ru.vsu.csf.twopeoplestudios.model.weapons.FlyingProjectile;
import ru.vsu.csf.twopeoplestudios.model.weapons.HeroAttack;

import java.util.concurrent.atomic.AtomicReference;

public class WorldContactListener implements ContactListener {

    //Своеобразный паттерн: любой из таких методов будет смотреть, что с чем столкнулось, если то, с чем метод должен работать,
    // то он определит, что из объектов - что, и вернет правду.
    private boolean ifHeroStandsOnHerb(Contact contact, AtomicReference<Hero> hero, AtomicReference<Herb> herb) {
        Object firstData = contact.getFixtureA().getBody().getUserData();
        Object secondData = contact.getFixtureB().getBody().getUserData();

        if (firstData == null || secondData == null)
            return false;

        if (firstData instanceof HerbUserData && secondData instanceof HeroUserData) {
            herb.set(((HerbUserData) firstData).getParent());
            hero.set(((HeroUserData) secondData).getParent());
            return true;
        } else if (firstData instanceof HeroUserData && secondData instanceof HerbUserData) {
            hero.set(((HeroUserData) firstData).getParent());
            herb.set(((HerbUserData) secondData).getParent());
            return true;
        } else
            return false;
    }

    private boolean ifHeroAttacksEnemy(Contact contact, AtomicReference<Monster> monster, AtomicReference<HeroAttack> attack){
        Object firstData = contact.getFixtureA().getBody().getUserData();
        Object secondData = contact.getFixtureB().getBody().getUserData();

        if (firstData == null || secondData == null)
            return false;

        if (firstData instanceof HeroAttack && secondData instanceof Monster) {
            attack.set((HeroAttack) firstData);
            monster.set((Monster) secondData);
            return true;
        } else if (firstData instanceof Monster && secondData instanceof HeroAttack) {
            monster.set((Monster) firstData);
            attack.set((HeroAttack) secondData);
            return true;
        } else
            return false;
    }

    private boolean ifHeroShootsEnemy(Contact contact, AtomicReference<Monster> monster, AtomicReference<FlyingProjectile> projectile){
        Object firstData = contact.getFixtureA().getBody().getUserData();
        Object secondData = contact.getFixtureB().getBody().getUserData();

        if (firstData == null || secondData == null)
            return false;

        if (firstData instanceof FlyingProjectile && secondData instanceof Monster) {
            projectile.set((FlyingProjectile) firstData);
            monster.set((Monster) secondData);
            return true;
        } else if (firstData instanceof Monster && secondData instanceof FlyingProjectile) {
            monster.set((Monster) firstData);
            projectile.set((FlyingProjectile) secondData);
            return true;
        } else
            return false;
    }

    private boolean ifEnemySeesHero(Contact contact, AtomicReference<Monster> monster, AtomicReference<Hero> hero) {
        Object firstData = contact.getFixtureA().getBody().getUserData();
        Object secondData = contact.getFixtureB().getBody().getUserData();

        if (firstData == null || secondData == null)
            return false;

        if (firstData instanceof MonsterHeroVision && secondData instanceof HeroUserData) {
            monster.set(((MonsterHeroVision) firstData).parent);
            hero.set(((HeroUserData) secondData).getParent());
            return true;
        } else if (firstData instanceof HeroUserData && secondData instanceof MonsterHeroVision) {
            hero.set(((HeroUserData) firstData).getParent());
            monster.set(((MonsterHeroVision) secondData).parent);
            return true;
        } else
            return false;
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void beginContact(Contact contact) {
        AtomicReference<Hero> hero = new AtomicReference<Hero>(); //штуки вместо ref-передачи аргументов в методы
        AtomicReference<Herb> herb = new AtomicReference<Herb>();
        AtomicReference<Monster> monster = new AtomicReference<Monster>();
        AtomicReference<HeroAttack> attack = new AtomicReference<HeroAttack>();
        AtomicReference<FlyingProjectile> projectile = new AtomicReference<FlyingProjectile>();

        if (ifHeroStandsOnHerb(contact, hero, herb)) {
            hero.get().onTouchHerb(herb.get());
        }
        else if (ifHeroAttacksEnemy(contact, monster, attack)) {
            monster.get().beAttacked();
        }
        else if (ifEnemySeesHero(contact, monster, hero)) {
            monster.get().seeHero(hero.get().getHeroPosition());
        }
        else if (ifHeroShootsEnemy(contact, monster, projectile)) {
            monster.get().beAttacked();
        }
    }

    @Override
    public void endContact(Contact contact) {
        AtomicReference<Hero> hero = new AtomicReference<Hero>();
        AtomicReference<Herb> herb = new AtomicReference<Herb>();
        AtomicReference<Monster> monster = new AtomicReference<Monster>();

        if (ifHeroStandsOnHerb(contact, hero, herb)) {
            hero.get().onStopTouchingHerb();
        }
        else if (ifEnemySeesHero(contact, monster, hero)) {
            monster.get().stopSeeHero();
        }
    }


    //вряд ли будем юзать
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
