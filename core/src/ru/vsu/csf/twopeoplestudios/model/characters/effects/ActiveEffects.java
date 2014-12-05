package ru.vsu.csf.twopeoplestudios.model.characters.effects;

import java.util.ArrayList;
import java.util.List;

public class ActiveEffects {

    public ArrayList<Effect> activeEffects;

    public ActiveEffects() {
        activeEffects = new ArrayList<Effect>();
    }

    public ActiveEffects(List<Effect> activeEffects) {
        this();
        this.activeEffects.addAll(activeEffects);
    }
}
