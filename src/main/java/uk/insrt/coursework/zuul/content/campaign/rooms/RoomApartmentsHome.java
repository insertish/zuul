package uk.insrt.coursework.zuul.content.campaign.rooms;

import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.EntityObject;
import uk.insrt.coursework.zuul.events.EventTick;
import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

public class RoomApartmentsHome extends Room {
    public RoomApartmentsHome(World world) {
        super(world, "Apartments: Home");
    }
    
    public String describe() {
        return this.getName();
    }

    protected void setupDirections() {
        this.setAdjacent(Direction.DOWN, this.getWorld().getRoom("Apartments: Reception"));
    }

    public void spawnEntities() {
        World world = this.getWorld();

        world.spawnEntity("bed", new EntityObject(world, this.toLocation(), 80, new String[] { "bed" }, "Bed") {
            @Override
            public boolean use(Entity target) {
                // for example, we could move the world forwards by 20 ticks
                for (int i=0;i<20;i++) {
                    world.emit(new EventTick());
                }

                System.out.println("You take a nap.");
                return true;
            }
        });

        world.spawnEntity("laptop", new EntityObject(world, this.toLocation(), 2, new String[] { "laptop" }, "Laptop"));
    }
}
