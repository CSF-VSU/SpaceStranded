package ru.vsu.csf.twopeoplestudios.model.characters.effects;

import ru.vsu.csf.twopeoplestudios.model.characters.Hero;

public class Effect {

    protected float delay = 0;
    protected float timeLeft = 0;

    public float duration = 60;

    public void onAttach(Hero hero) {}
    public void onDetach(Hero hero) {}
    public void onActionOnTime(Hero hero, float delay) {}
}
