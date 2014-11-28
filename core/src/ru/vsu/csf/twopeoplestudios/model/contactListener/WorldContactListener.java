package ru.vsu.csf.twopeoplestudios.model.contactListener;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import ru.vsu.csf.twopeoplestudios.model.characters.Hero;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.Herb;
import ru.vsu.csf.twopeoplestudios.model.contactListener.collisionUserData.HerbUserData;
import ru.vsu.csf.twopeoplestudios.model.contactListener.collisionUserData.HeroUserData;

import java.util.concurrent.atomic.AtomicReference;

public class WorldContactListener implements ContactListener {

    //Своеобразный паттерн: любой из таких методов будет смотреть, что с чем столкнулось, если то, с чем метод должен работать,
    // то он определит, что из объектов - что, и вернет правду.
    private boolean ifHeroStandsOnHerb(Contact contact, AtomicReference<Hero> hero, AtomicReference<Herb> herb) {
        Object firstData = contact.getFixtureA().getBody().getUserData();
        Object secondData = contact.getFixtureB().getBody().getUserData();

        if (firstData == null || secondData == null)
            return false;

        if (firstData.getClass().equals(HerbUserData.class) && secondData.getClass().equals(HeroUserData.class)) {
            herb.set(((HerbUserData) firstData).getParent());
            hero.set(((HeroUserData) secondData).getParent());
            return true;
        } else if (firstData.getClass().equals(HeroUserData.class) && secondData.getClass().equals(HerbUserData.class)) {
            hero.set(((HeroUserData) firstData).getParent());
            herb.set(((HerbUserData) secondData).getParent());
            return true;
        } else return false;
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void beginContact(Contact contact) {
        AtomicReference<Hero> hero = new AtomicReference<Hero>(); //штуки вместо ref-передачи аргументов в методы
        AtomicReference<Herb> herb = new AtomicReference<Herb>();

        if (ifHeroStandsOnHerb(contact, hero, herb)) {
            hero.get().onTouchHerb(herb.get());
            //System.out.println("Was contact!");
        }
    }

    @Override
    public void endContact(Contact contact) {
        AtomicReference<Hero> hero = new AtomicReference<Hero>();
        AtomicReference<Herb> herb = new AtomicReference<Herb>();

        if (ifHeroStandsOnHerb(contact, hero, herb)) {
            hero.get().onStopTouchingHerb();
            //System.out.println("Was contact!");
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
