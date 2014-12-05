package ru.vsu.csf.twopeoplestudios.model.collectibles.herbs;

import java.util.ArrayList;
import java.util.List;

public class KnownHerb {
    public int id;
    public ArrayList<HerbProperties> properties;

    public KnownHerb(int id, List<HerbProperties> properties) {
        this.id = id;
        this.properties.addAll(properties);
    }
}
