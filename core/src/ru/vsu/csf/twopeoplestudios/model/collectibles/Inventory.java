package ru.vsu.csf.twopeoplestudios.model.collectibles;

public class Inventory {

    protected Collectible[] data;

    public Collectible[] getData() {
        return data;
    }

    public Inventory() {
        data = new Collectible[40];
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
