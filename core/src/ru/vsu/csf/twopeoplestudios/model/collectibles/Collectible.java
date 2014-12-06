package ru.vsu.csf.twopeoplestudios.model.collectibles;

public class Collectible {

    protected int id;
    protected int count;

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public int decreaseCount(int amount) {
        this.count -= amount;
        return this.count;
    }

    public Collectible(int id, int count) {
        this.id = id;
        this.count = count;
    }
}
