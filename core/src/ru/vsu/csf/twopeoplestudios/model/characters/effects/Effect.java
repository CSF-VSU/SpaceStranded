package ru.vsu.csf.twopeoplestudios.model.characters.effects;

import ru.vsu.csf.twopeoplestudios.model.characters.Hero;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.HerbProperty;

public class Effect {

    public static final float STANDART_EFFECT_DURATION = 60;

    public HerbProperty property;

    protected float delay = 0;
    protected float timeLeft = 0;

    public float duration = STANDART_EFFECT_DURATION;

    public void onAttach(Hero hero) {}
    public void onDetach(Hero hero) {}
    public void onActionOnTime(Hero hero, float delay) {} //todo
}
