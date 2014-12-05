package ru.vsu.csf.twopeoplestudios.model.characters.effects;

import ru.vsu.csf.twopeoplestudios.model.characters.Hero;

public abstract class Effect {

    protected float delay = 0;
    protected float timeLeft = 0;

    public abstract void onAttach(Hero hero);
    public abstract void onDetach(Hero hero);
    public abstract void onActionOnTime(Hero hero, float delay);
}
