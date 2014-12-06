package ru.vsu.csf.twopeoplestudios.model.collectibles.herbs;

import java.util.ArrayList;

public class Herbs {

    public ArrayList<HerbInfo> herbs;

    private Herbs() {
        herbs = new ArrayList<HerbInfo>() {{
            add(new HerbInfo(0, new ArrayList<HerbProperty>() {{
                add(HerbProperty.HP_UP);
            }}));
            add(new HerbInfo(1, new ArrayList<HerbProperty>() {{
                add(HerbProperty.HP_DOWN);
                add(HerbProperty.ST_DOWN);
            }}));
            add(new HerbInfo(2, new ArrayList<HerbProperty>() {{
                add(HerbProperty.ST_UP);
            }}));
        }};
    }

    private static Herbs instance;

    public static Herbs getInstance() {
        if (instance == null)
            instance = new Herbs();
        return  instance;
    }

    public ArrayList<HerbProperty> getPropertiesOfHerb(int id) {
        return herbs.get(id).getProperties();
    }
}