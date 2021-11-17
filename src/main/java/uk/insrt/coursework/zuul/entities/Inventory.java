package uk.insrt.coursework.zuul.entities;

import java.util.ArrayList;

/**
 * Representation of an Entity's inventory
 * and what they are holding.
 */
public class Inventory {
    private ArrayList<Entity> items = new ArrayList<>();
    private int maxWeight;

    /**
     * Construct a new Inventory.
     */
    public Inventory() {
        super();
        this.maxWeight = 0;
    }

    /**
     * Set the max weight that can be carried in this inventory.
     * @param maxWeight Max weight (in kg)
     */
    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    /**
     * Get the current weight of this inventory.
     * @return Weight (in kg)
     */
    private int getWeight() {
        return this
            .items
            .stream()
            .mapToInt(Entity::getWeight)
            .sum();
    }

    /**
     * Add an entity to this inventory.
     * 
     * There must be sufficient space for the entity.
     * @param entity Target Entity
     * @return Whether we successfully added the new entity.
     */
    public boolean add(Entity entity) {
        if (this.getWeight() + entity.getWeight() > this.maxWeight) {
            return false;
        }

        this.items.add(entity);
        return true;
    }

    /**
     * Remove an entity from this inventory.
     * @param entity Target Entity
     * @return Whether there was any change to the inventory.
     */
    public boolean remove(Entity entity) {
        return this.items.remove(entity);
    }
}
