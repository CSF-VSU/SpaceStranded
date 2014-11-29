package ru.vsu.csf.twopeoplestudios.model.collectibles.herbs;

import java.util.ArrayList;

public class HerbStorage {

    ArrayList<HerbInfo> herbs;


    private HerbStorage() {
        herbs = new ArrayList<HerbInfo>();

    }

    private static HerbStorage instance;

    public static HerbStorage getInstance() {
        if (instance == null)
            instance = new HerbStorage();
        return instance;
    }

    /*public TextureRegion getTexture(int id) {
        return textures.get(id);
    }*/
}