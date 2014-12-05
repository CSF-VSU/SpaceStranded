package ru.vsu.csf.twopeoplestudios.model.collectibles.herbs;

import java.util.ArrayList;

public class Herbs {

    ArrayList<HerbInfo> herbs;

    private Herbs() {
        herbs = new ArrayList<HerbInfo>() {{
            add(new HerbInfo(0, new ArrayList<HerbProperties>() {{
                add(HerbProperties.HP_UP);
            }}));
            add(new HerbInfo(1, new ArrayList<HerbProperties>() {{
                add(HerbProperties.HP_DOWN);
                add(HerbProperties.ST_DOWN);
            }}));
            add(new HerbInfo(0, new ArrayList<HerbProperties>() {{
                add(HerbProperties.ST_UP);
            }}));
        }};
    }

    private static Herbs instance;

    public static Herbs getInstance() {
        if (instance != null)
            instance = new Herbs();
        return  instance;
    }
}