package ru.vsu.csf.twopeoplestudios.model.collectibles.herbs;

import ru.vsu.csf.twopeoplestudios.model.characters.Hero;
import ru.vsu.csf.twopeoplestudios.model.characters.effects.Effect;

public enum HerbProperties {

    HP_UP (new Effect() {
        @Override
        public void onAttach(Hero hero) {
            hero.increaseMaxHp(10);
        }

        @Override
        public void onDetach(Hero hero) {
            hero.decreaseMaxHp(10);
        }
    }),
    HP_DOWN (new Effect() {
        @Override
        public void onAttach(Hero hero) {
            hero.decreaseMaxHp(10);
        }

        @Override
        public void onDetach(Hero hero) {
            hero.increaseMaxHp(10);
        }
    }),

    FL_UP (new Effect() {
        @Override
        public void onAttach(Hero hero) {
            hero.increaseMaxFl(10);
        }

        @Override
        public void onDetach(Hero hero) {
            hero.decreaseMaxFl(10);
        }
    }),
    FL_DOWN (new Effect() {
        @Override
        public void onAttach(Hero hero) {
            hero.decreaseMaxFl(10);
        }

        @Override
        public void onDetach(Hero hero) {
            hero.increaseMaxFl(10);
        }
    }),

    ST_UP  (new Effect() {
        @Override
        public void onAttach(Hero hero) {
            hero.increaseMaxSt(10);
        }

        @Override
        public void onDetach(Hero hero) {
            hero.decreaseMaxSt(10);
        }
    }),
    ST_DOWN (new Effect() {
        @Override
        public void onAttach(Hero hero) {
            hero.decreaseMaxSt(10);
        }

        @Override
        public void onDetach(Hero hero) {
            hero.increaseMaxSt(10);
        }
    })
    ;

    private final Effect effect;

    HerbProperties(Effect effect) {
        this.effect = effect;
    }

    public Effect getEffect() {
        return effect;
    }
}
