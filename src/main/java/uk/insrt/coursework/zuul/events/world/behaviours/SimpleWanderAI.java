package uk.insrt.coursework.zuul.events.world.behaviours;

import java.util.Random;

import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.events.IEventListener;
import uk.insrt.coursework.zuul.events.world.EventTick;
import uk.insrt.coursework.zuul.world.Room;

/**
 * This is a simple behaviour which just randomly decides to move an Entity
 * through a set path whenever the game ticks forwards.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class SimpleWanderAI implements IEventListener<EventTick> {
    private Entity entity;
    private Room[] path;
    private int chance;

    private int index;
    private Random random;

    /**
     * Construct a new wandering behaviour for an Entity with a given path.
     * @param entity Entity which should be moved
     * @param path Path that this Entity should follow
     * @param chance The chance x that this entity moves, where x gives a 1/x fractional chance of moving.
     */
    public SimpleWanderAI(Entity entity, Room[] path, int chance) {
        this.entity = entity;
        this.path = path;
        this.chance = chance;

        this.index = 0;
        this.random = new Random();
    }

    @Override
    public void onEvent(EventTick event) {
        if (this.entity.getRoom() != this.path[this.index]) return;
        if (random.nextInt(this.chance) > 0) return;

        this.index = (this.index + 1) % this.path.length;
        this.entity.setLocation(this.path[this.index]);
    }
}
