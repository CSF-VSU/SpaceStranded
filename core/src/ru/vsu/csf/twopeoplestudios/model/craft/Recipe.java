package ru.vsu.csf.twopeoplestudios.model.craft;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    public int id;
    public ArrayList<CraftPart> in;
    public CraftPart out;
    public boolean isTransformation; //можно ли сделать вещь в инвентаре

    public Recipe(int id, List<CraftPart> requirements, CraftPart out, boolean isTransformation) {
        this.id = id;
        in = new ArrayList<CraftPart>();
        in.addAll(requirements);

        this.out = out;
        this.isTransformation = isTransformation;
    }
}

