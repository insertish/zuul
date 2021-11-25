package uk.insrt.coursework.zuul.content.campaign.entities;

import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.actions.IGiveable;
import uk.insrt.coursework.zuul.entities.actions.IUseable;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

public class EntityBoat extends Entity implements IUseable, IGiveable {
    private Room destination;

    public EntityBoat(World world, Location location, Room destination) {
        super(world, location, 200);
        this.destination = destination;
        this.inventory.setMaxWeight(100);
    }

    @Override
    public String[] getAliases() {
        return new String[] { "boat" };
    }

    @Override
    public String describe() {
        return "boat";
    }

    public void use(Entity target) {
        target.setLocation(this.destination);
    }

    public void give(Entity item) {
        var io = this.getWorld().getIO();
        if (item.setLocation(this.getInventory())) {
            io.println("Put " + item.getHighlightedName() + " in boat.");
        } else {
            io.println("The boat is carrying too much stuff already!");
        }
    }
}
