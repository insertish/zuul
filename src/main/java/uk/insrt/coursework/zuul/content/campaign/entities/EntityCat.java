package uk.insrt.coursework.zuul.content.campaign.entities;

import uk.insrt.coursework.zuul.behaviours.SimpleWanderAI;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.actions.IPettable;
import uk.insrt.coursework.zuul.entities.actions.IUseable;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

/**
 * Cat entity which wanders around the map.
 */
public class EntityCat extends Entity implements IPettable, IUseable {
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

    public void pet() {
        this.getWorld().getIO().println("You pet the cat.");
    }

    public void useWanderAI(Room[] rooms, int chance) {
        this.getWorld()
            .getEventSystem()
            .onTick(new SimpleWanderAI(this, rooms, chance));
    }

    @Override
    public void use(Entity target) {
        this.getWorld().getIO().println("You cannot the cat.");
    }
}