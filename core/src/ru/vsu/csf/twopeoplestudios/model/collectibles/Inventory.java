package ru.vsu.csf.twopeoplestudios.model.collectibles;

import ru.vsu.csf.twopeoplestudios.model.characters.Hero;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.HerbProperty;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.Herbs;

public class Inventory extends Panel {

    public static final int WIDTH = 10;
    public static final int HEIGHT = 4;

    public int selectedRow, selectedColumn;

    private Hero hero;

    public Inventory(Hero hero) {
        data = new Collectible[40];
        selectedRow = 0;
        selectedColumn = 0;

        this.hero = hero;
    }

    public void selectItem(int i, int j) {
        selectedRow = j;
        selectedColumn = i;
    }

    @Override
    public Collectible getSelectedItem() {
        return data[selectedRow * WIDTH + selectedColumn];
    }


    public void consume() {
        Collectible item = getSelectedItem();

        if (Items.getInstance().isHerb(item.getId())) {
            hero.inflictDamage(20);

            hero.revealHerbProperties(item.getId());

            for (HerbProperty property : Herbs.getInstance().getPropertiesOfHerb(item.id)) {
                hero.addActiveEffect(property.getEffect());
            }

            if (item.decreaseCount(1) == 0) {
                drop(selectedRow * WIDTH + selectedColumn);
                selectedRow = 0;
                selectedColumn = 0;
            }
        }
    }

    public boolean has(int id, int count) {
        for (Collectible c : data)
            if (c != null && c.getId() == id && c.getCount() >= count)
                return true;
        return false;
    }
}
