package uk.insrt.coursework.zuul.content.campaign.rooms;

import java.util.Random;

import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.events.IEventListener;
import uk.insrt.coursework.zuul.events.world.EventEntityEnteredRoom;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

public class RoomWormHole extends CampaignRoom implements IEventListener<EventEntityEnteredRoom> {
    public RoomWormHole(World world) {
        super(world, "Worm Hole");
    }
    
    public String describe() {
        return this.getName();
    }

    protected void setupDirections() {}

    @Override
    public void onEvent(EventEntityEnteredRoom event) {
        Entity entity = event.getEntity();
        Room room = entity.getRoom();
        if (room != this) return;
        event.stopPropagation();

        final Random random = new Random();
        final String[] locations = {
            "City Centre",
            "Coastline",
            "Mainland: Coastline",
            "Forest",
            "Street",
            "Back Alley"
        };

        var io = this.getWorld().getIO();
        io.println("\nYou step into the worm hole...\n");

        try {
            Thread.sleep(1000);
        } catch (Exception e) { }

        final int WIDTH = 79;

        // Transport animation, this will take 1800 ms.
        for (int i=0;i<5;i++) {
            io.println("*".repeat(i*3) + "\\" + " ".repeat(WIDTH - i * 6 - 2) + "/" + "*".repeat(i*3));
            try {
                Thread.sleep(60);
            } catch (Exception e) { }
        }

        for (int i=0;i<30;i++) {
            var out = "";
            for (int j=0;j<WIDTH;j++) {
                out += random.nextInt(8) == 0 ? "*" : " ";
            }

            io.println(out);

            try {
                Thread.sleep(40);
            } catch (Exception e) { }
        }

        for (int i=5;i>0;i--) {
            io.println("*".repeat(i*3) + "/" + " ".repeat(WIDTH - i * 6 - 2) + "\\" + "*".repeat(i*3));
            try {
                Thread.sleep(60);
            } catch (Exception e) { }
        }
        
        io.print("\n");
        
        String location = locations[random.nextInt(locations.length)];
        Room target = this.getWorld().getRoom(location);
        entity.setLocation(target);
    }
}
