package uk.insrt.coursework.zuul.entities;

import uk.insrt.coursework.zuul.behaviours.SimpleWanderAI;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

/**
 * Cat entity which wanders around the map.
 */
public class EntityCat extends Entity {
    public EntityCat(World world, Location startingLocation) {
        super(world, startingLocation, 5);
    }

    @Override
    public String[] getAliases() {
        return new String[] {
            "cat",
            "the cat"
        };
    }

    @Override
    public String describe() {
        return "A black cat";
    }

    @Override
    public boolean pet() {
        System.out.println("You pet the cat.");
        return true;
    }

    @Override
    public boolean use(Entity target) {
        return false;
    }

    public void useWanderAI(Room[] rooms, int chance) {
        this.getWorld()
            .getEventSystem()
            .onTick(new SimpleWanderAI(this, rooms, chance));
    }
}
