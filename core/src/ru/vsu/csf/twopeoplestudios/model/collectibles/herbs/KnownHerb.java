package ru.vsu.csf.twopeoplestudios.model.collectibles.herbs;

import java.util.ArrayList;
import java.util.List;

public class KnownHerb {
    public int id;
    public ArrayList<HerbProperty> properties;

    public KnownHerb(int id, final List<HerbProperty> properties) {
        this.id = id;
        this.properties = new ArrayList<HerbProperty>() {{
            addAll(properties);
        }};
    }
}
