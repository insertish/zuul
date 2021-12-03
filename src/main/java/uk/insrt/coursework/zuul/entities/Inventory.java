package uk.insrt.coursework.zuul.entities;

import java.util.ArrayList;

/**
 * Representation of an Entity's inventory
 * and what they are holding.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class Inventory {
    private ArrayList<Entity> items = new ArrayList<>();
    private double maxWeight;

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
    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    /**
     * Get the maximum weight that can be carried in this inventory.
     * @return Maximum weight that can be carried
     */
    public double getMaxWeight() {
        return this.maxWeight;
    }

    /**
     * Get the current weight of this inventory.
     * @return Weight (in kg)
     */
    public double getWeight() {
        return this
            .items
            .stream()
            .mapToDouble(Entity::getWeight)
            .sum();
    }

    /**
     * Check if the inventory is full.
     * @return True if the weight is greater than the max weight
     */
    public boolean isFull() {
        return this.getWeight() >= this.getMaxWeight();
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

    /**
     * Get an Iterable over the Entities within this inventory.
     * @return Iterable over Entities
     */
    public Iterable<Entity> getItems() {
        return this.items;
    }
}
