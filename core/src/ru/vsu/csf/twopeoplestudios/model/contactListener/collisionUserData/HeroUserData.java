package ru.vsu.csf.twopeoplestudios.model.contactListener.collisionUserData;

import ru.vsu.csf.twopeoplestudios.model.characters.Hero;

public class HeroUserData {
    private Hero parent;

    public HeroUserData(Hero parent) {
        this.parent = parent;
    }

    public Hero getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "HeroUserData{" +
                "parent=" + parent +
                '}';
    }
}
