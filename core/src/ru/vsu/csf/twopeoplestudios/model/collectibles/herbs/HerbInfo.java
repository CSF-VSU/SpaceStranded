package ru.vsu.csf.twopeoplestudios.model.collectibles.herbs;

import java.util.ArrayList;

public class HerbInfo {

    int id;
    ArrayList<HerbProperty> properties;

    public HerbInfo(int id, ArrayList<HerbProperty> properties) {
        this.id = id;
        this.properties = properties;
    }

    public void setProperties(ArrayList<HerbProperty> properties) {
        this.properties = properties;
    }

    public ArrayList<HerbProperty> getProperties() {
        return properties;
    }
}
