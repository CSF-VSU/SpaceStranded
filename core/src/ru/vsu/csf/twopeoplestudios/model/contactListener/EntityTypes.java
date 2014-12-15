package ru.vsu.csf.twopeoplestudios.model.contactListener;

public class EntityTypes {
    //Флаги категорий: единичка в том или ином разряде (степени двойки)
    public static final short HERO        = 0x2;      // 0 0 0 0 | 0 0 1 0
    public static final short HERB        = 0x4;      // 0 0 0 0 | 0 1 0 0
    public static final short ENEMY       = 0x8;      // 0 0 0 0 | 1 0 0 0
    public static final short HERO_ATK    = 0x10;     // 0 0 0 1 | 0 0 0 0

    //Флаги масок: бинарное сложение тех категорий, с которыми должен сталкиваться объект
    //boolean столкновениеМожетБыть = (Category_1 & Mask_2) & (Category_2 & Mask_1)
    public static final short HERO_MASK = HERB | ENEMY;
    public static final short HERB_MASK = HERO;
    public static final short ENEMY_MASK = HERO | HERO_ATK;
    public static final short HERO_ATK_MASK = ENEMY;
}
