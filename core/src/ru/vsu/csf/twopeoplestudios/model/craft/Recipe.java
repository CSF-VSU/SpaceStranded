package ru.vsu.csf.twopeoplestudios.model.craft;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    public ArrayList<CraftPart> in;
    public CraftPart out;
    public boolean isTransformation; //можно ли сделать вещь в инвентаре

    public Recipe(List<CraftPart> requirements, CraftPart out, boolean isTransformation) {
        in = new ArrayList<CraftPart>();
        in.addAll(requirements);

        this.out = out;
        this.isTransformation = isTransformation;
    }
}

