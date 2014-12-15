package ru.vsu.csf.twopeoplestudios.model.weapons;

import ru.vsu.csf.twopeoplestudios.model.characters.Hero;
import ru.vsu.csf.twopeoplestudios.model.collectibles.Collectible;

public class MeleeWeapon extends Collectible {

    private MeleeAttackType attackType;
    private Hero hero;

    public MeleeWeapon(int id, Hero hero) {
        super(id, 1);

        this.hero = hero;
        this.attackType = Weapons.getInstance().getMeleeAttackType(this.id);
    }

    public void attack() {
        hero.getAttack().init(HeroAttacks.attacks.get(attackType).get(hero.getFacing()));
    }
}
