package ru.vsu.csf.twopeoplestudios.model.collectibles;

public class Inventory {

    private ICollectible[] data;

    public Inventory() {
        data = new ICollectible[40];
    }

    public int getEmptySlot() {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null)
                return i;
        }
        return -1;
    }

    public boolean tryToPut(ICollectible collectible) {
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
