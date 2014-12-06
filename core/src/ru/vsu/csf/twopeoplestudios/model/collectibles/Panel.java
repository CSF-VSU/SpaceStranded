package ru.vsu.csf.twopeoplestudios.model.collectibles;

public class Panel {

    protected Collectible[] data;
    protected int selectedIndex;

    public Collectible[] getData() {
        return data;
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

    public boolean tryToPut(Collectible collectible) {
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

    public void drop(int index) {
        if (data[index] == null)
            return;
        data[index] = null;
    }
}
