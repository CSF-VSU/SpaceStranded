package ru.vsu.csf.twopeoplestudios.model.collectibles.herbs;

import java.util.ArrayList;
import java.util.List;

public class KnownHerb {
    public int id;
    public ArrayList<HerbProperties> properties;

    public KnownHerb(int id, final List<HerbProperties> properties) {
        this.id = id;
        this.properties = new ArrayList<HerbProperties>() {{
            addAll(properties);
        }};
    }
}
