package ru.vsu.csf.twopeoplestudios.model.contactListener.collisionUserData;

import ru.vsu.csf.twopeoplestudios.model.map.Map;

public class ProjectileUserData {

    private Map parent;

    public ProjectileUserData(Map parent) {
        this.parent = parent;
    }

    public Map getParent() {
        return parent;
    }
}
