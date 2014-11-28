package ru.vsu.csf.twopeoplestudios.model.contactListener;

public class EntityTypes {
    //Флаги категорий: единичка в том или ином разряде (степени двойки)
    public static final int HERO = 2; // 0 0 1 0
    public static final int HERB = 4; // 0 1 0 0

    //Флаги масок: бинарное сложение тех категорий, с которыми должен сталкиваться объект
    //boolean столкновениеМожетБыть = (Category_1 & Mask_2) & (Category_2 & Mask_1)
    public static final int HERO_MASK = HERB;
    public static final int HERB_MASK = HERO;
}
