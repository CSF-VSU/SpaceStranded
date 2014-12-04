package ru.vsu.csf.twopeoplestudios.model.collectibles.herbs;

import java.util.ArrayList;

public class HerbInfo {

    int id;
    ArrayList<HerbProperties> properties;

    public HerbInfo(int id, ArrayList<HerbProperties> properties) {
        this.id = id;
        this.properties = properties;
    }

    public void setProperties(ArrayList<HerbProperties> properties) {
        this.properties = properties;
    }

    public ArrayList<HerbProperties> getProperties() {
        return properties;
    }
}
