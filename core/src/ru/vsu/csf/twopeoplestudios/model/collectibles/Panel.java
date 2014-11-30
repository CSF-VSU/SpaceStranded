package ru.vsu.csf.twopeoplestudios.model.collectibles;

public class Panel extends Inventory {

    private int selectedIndex;

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public Panel() {
        data = new Collectible[10];
    }

    public void selectItem(int index) {
        index--;
        if (index == -1)
            index = 9;
        selectedIndex = index;
    }

    public Collectible getItem() {
        return data[selectedIndex];
    }
}
