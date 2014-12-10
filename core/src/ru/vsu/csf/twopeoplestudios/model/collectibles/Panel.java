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
}
