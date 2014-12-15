package ru.vsu.csf.twopeoplestudios.model.weapons;

import java.util.HashMap;

public class Weapons {

    private static HashMap<Integer, MeleeAttackType> meleeWeaponsCharacteristics;

    private Weapons() {
        meleeWeaponsCharacteristics = new HashMap<Integer, MeleeAttackType>() {{
            put(250, MeleeAttackType.SLASH_ATTACK);
        }};
    }

    private static Weapons instance;

    public static Weapons getInstance() {
        if (instance == null)
            instance = new Weapons();
        return instance;
    }

    public MeleeAttackType getMeleeAttackType(int id) {
        return meleeWeaponsCharacteristics.get(id);
    }
}
