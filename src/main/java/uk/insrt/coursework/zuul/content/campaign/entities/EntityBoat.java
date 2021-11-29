package uk.insrt.coursework.zuul.content.campaign.entities;

import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.actions.IGiveable;
import uk.insrt.coursework.zuul.entities.actions.IUseable;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

/**
 * Boat entity which ferries the Player to an arbitrary location.
 * There is no restriction on location but they should be place as
 * appropriate and where it would be realistic to put a boat.
 * 
 * Boats may not be operated by the player while they are carrying
 * anything so instead they must use the boat's storage.
 */
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
        return "<entities.boat.description>";
    }

    @Override
    public void use(Entity target) {
        var io = this.getWorld().getIO();
        if (target.getInventory().getWeight() > 0) {
            io.println("<entities.boat.denied>");
            return;
        }

        io.println("<entities.boat.travel>\n");
        target.setLocation(this.destination);
    }

    @Override
    public void give(Entity item) {
        var io = this.getWorld().getIO();
        if (item.setLocation(this.getInventory())) {
            io.println("<entities.boat.give.1> " + item.getHighlightedName() + " <entities.boat.give.2>.");
        } else {
            io.println("<entities.boat.too_heavy>");
        }
    }
}
