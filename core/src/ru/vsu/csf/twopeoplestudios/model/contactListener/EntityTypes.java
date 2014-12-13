package ru.vsu.csf.twopeoplestudios.model.contactListener;

public class EntityTypes {
    //Флаги категорий: единичка в том или ином разряде (степени двойки)
    public static final int HERO        = 0x2;      // 0 0 0 0 | 0 0 1 0
    public static final int HERB        = 0x4;      // 0 0 0 0 | 0 1 0 0
    public static final int ENEMY       = 0x8;      // 0 0 0 0 | 1 0 0 0
    public static final int HERO_ATK    = 0x10;     // 0 0 0 1 | 0 0 0 0

    //Флаги масок: бинарное сложение тех категорий, с которыми должен сталкиваться объект
    //boolean столкновениеМожетБыть = (Category_1 & Mask_2) & (Category_2 & Mask_1)
    public static final int HERO_MASK = HERB | ENEMY;
    public static final int HERB_MASK = HERO;
    public static final int ENEMY_MASK = HERO | HERO_ATK;
    public static final int HERO_ATK_MASK = ENEMY;
}
