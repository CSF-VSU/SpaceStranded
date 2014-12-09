package ru.vsu.csf.twopeoplestudios.model.craft;

import ru.vsu.csf.twopeoplestudios.model.collectibles.Collectible;
import ru.vsu.csf.twopeoplestudios.model.collectibles.Inventory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Recipes {
    public ArrayList<Recipe> recipes;

    private Recipes() {
        recipes = new ArrayList<Recipe>() {{ //todo: init from file
            add(new Recipe(new ArrayList<CraftPart>() {{  // ore -> metal
                add(new CraftPart(102, 1));
            }}, new CraftPart(103, 1), true));

            add(new Recipe(new ArrayList<CraftPart>() {{  // metal -> scrap
                add(new CraftPart(103, 1));
            }}, new CraftPart(105, 1), true));

            add(new Recipe(new ArrayList<CraftPart>() {{  // scrap -> forcer*2
                add(new CraftPart(105, 1));
            }}, new CraftPart(106, 2), true));

            add(new Recipe(new ArrayList<CraftPart>() {{  // scrap -> gear*3
                add(new CraftPart(105, 1));
            }}, new CraftPart(107, 3), true));

            add(new Recipe(new LinkedList<CraftPart>() {{ //2*metal -> cup
                add(new CraftPart(103, 2));
            }}, new CraftPart(104, 1), false));

            add(new Recipe(new LinkedList<CraftPart>() {{ //metal -> battery
                add(new CraftPart(103, 1));
            }}, new CraftPart(100, 1), false));

            add(new Recipe(new LinkedList<CraftPart>() {{ //ore -> scanner
                add(new CraftPart(102, 1));
            }}, new CraftPart(101, 1), false));
        }};
    }

    private static Recipes instance;

    public static Recipes get() {
        if (instance == null)
            instance = new Recipes();
        return instance;
    }


    public boolean ifItemTransformable(int id) {
        for (Recipe recipe : recipes) {
            if (recipe.isTransformation && recipe.in.get(0).id == id)
                return true;
        }
        return false;
    }

    public List<Integer> getIDsOfTransformResult(int id) {
        LinkedList<Integer> result = new LinkedList<Integer>();
        for (Recipe recipe : recipes) {
            if (recipe.isTransformation && recipe.in.get(0).id == id)
                result.add(recipe.out.id);
        }
        return result;
    }

    public List<Recipe> getAllPossibleProducts(Inventory inventory) {
        LinkedList<Recipe> result = new LinkedList<Recipe>();

        for (Recipe r : recipes) {
            boolean has = true;
            for (CraftPart part : r.in) {
                if (!inventory.has(part.id, part.count)) {
                    has = false;
                    break;
                }
            }
            if (has)
                result.add(r);
        }

        return result;
    }
}
