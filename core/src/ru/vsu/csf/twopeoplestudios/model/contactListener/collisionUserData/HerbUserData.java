package ru.vsu.csf.twopeoplestudios.model.contactListener.collisionUserData;

import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.Herb;

public class HerbUserData {
    private Herb parent;

    public HerbUserData(Herb parent) {
        this.parent = parent;
    }

    public Herb getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "HerbUserData{" +
                "parent=" + parent +
                '}';
    }
}
