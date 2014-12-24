package ru.vsu.csf.twopeoplestudios.model.collectibles;

import ru.vsu.csf.twopeoplestudios.model.characters.Hero;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.HerbProperty;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.Herbs;

public class Panel {

    public static final int WIDTH = 10;
    public static final int HEIGHT = 1;

    protected Collectible[] data;
    protected int selectedIndex;

    protected Hero hero;

    public Collectible[] getData() {
        return data;
    }

    public Panel(Hero hero) {
        data = new Collectible[10];
        this.hero = hero;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public void selectItemFromKey(int index) {
        index--;
        if (index == -1)
            index = 9;
        selectedIndex = index;
    }

    public Collectible getSelectedItem() {
        return data[selectedIndex];
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public int getEmptySlot() {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null)
                return i;
        }
        return -1;
    }

    public boolean add(Collectible collectible) {
        if (Items.getInstance().checkIfCountable(collectible.id)) {
            for (Collectible c : data) {
                if (c != null && c.id == collectible.id) {
                    c.count += collectible.count;
                    return true;
                }
            }
        }

        int index = getEmptySlot();
        if (index == -1)
            return false;
        data[index] = collectible;
        return true;
    }

    public int getItemIndex(int itemId) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] != null && data[i].getId() == itemId)
                return i;
        }
        return -1;
    }

    public void drop(int index) {
        if (data[index] == null)
            return;
        data[index] = null;
    }

    public void take(int index, int amount) {
        if (data[index].count == amount)
            drop(index);
        else {
            data[index].count -= amount;
            if (data[index].count == 0)
                data[index] = null;
        }
    }

    public int getItemCount(int id) {
        for (Collectible c : data) {
            if (c != null && c.getId() == id)
                return c.count;
        }
        return 0;
    }

    public boolean isSelectedCellEmpty() {
        return data[selectedIndex] == null;
    }

    public void destroyItemInSelectedIndex() {
        data[selectedIndex] = null;
    }

    public void putInSelectedCell(Collectible item) {
        data[selectedIndex] = item;
    }

    protected boolean consumeSelected(Collectible item) {
        if (Items.getInstance().isHerb(item.getId())) {
            hero.inflictDamage(20);

            hero.revealHerbProperties(item.getId());

            for (HerbProperty property : Herbs.getInstance().getPropertiesOfHerb(item.id)) {
                hero.addActiveEffect(property.getEffect());
            }

            return true;
        }
        return false;
    }

    public void consume() {
        Collectible item = getSelectedItem();
        if (consumeSelected(item)) {
            if (item.decreaseCount(1) == 0) {
                drop(selectedIndex);
                selectedIndex = 0;
            }
        }
    }
}
