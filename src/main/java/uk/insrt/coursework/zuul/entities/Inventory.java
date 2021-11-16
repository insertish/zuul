package uk.insrt.coursework.zuul.entities;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Entity> items = new ArrayList<>();
    private int maxWeight;

    public Inventory() {
        super();
        this.maxWeight = 0;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    private int getWeight() {
        return this
            .items
            .stream()
            .mapToInt(Entity::getWeight)
            .sum();
    }

    public boolean add(Entity entity) {
        if (this.getWeight() + entity.getWeight() > this.maxWeight) {
            return false;
        }

        this.items.add(entity);
        return true;
    }

    public boolean remove(Entity entity) {
        return this.items.remove(entity);
    }
}
