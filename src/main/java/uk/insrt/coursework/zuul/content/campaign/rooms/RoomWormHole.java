package uk.insrt.coursework.zuul.content.campaign.rooms;

import java.util.Random;

import uk.insrt.coursework.zuul.content.campaign.StoryFlags.Quest;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.EntityPlayer;
import uk.insrt.coursework.zuul.events.IEventListener;
import uk.insrt.coursework.zuul.events.world.EventEntityEnteredRoom;
import uk.insrt.coursework.zuul.sound.EventSound;
import uk.insrt.coursework.zuul.sound.SoundType;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

/**
 * Teleporter room implemented as required by the challenge tasks.
 * Any Entity that walks into the worm hole is transported into a random public location.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class RoomWormHole extends CampaignRoom implements IEventListener<EventEntityEnteredRoom> {
    public RoomWormHole(World world) {
        super(world, "Worm Hole");
    }
    
    @Override
    public String describe() {
        return "";
    }

    @Override
    protected void setupDirections() {}

    @Override
    public void onEvent(EventEntityEnteredRoom event) {
        Entity entity = event.getEntity();
        Room room = entity.getRoom();
        if (room != this) return;
        event.stopPropagation();

        // This is a restricted set of locations as to not break
        // the game's plot, say if we were transported to the medical
        // centre complex office when we're not meant to go there yet.
        final Random random = new Random();
        final String[] locations = {
            "City Centre",
            "Coastline",
            "Mainland: Coastline",
            "Forest",
            "Street",
            "Back Alley"
        };

        var world = this.getWorld();
        var io = world.getIO();
        io.println("\n<worm_hole.enter>");

        try {
            Thread.sleep(1000);

            final int WIDTH = 79;

            for (int i=0;i<5;i++) {
                io.println("*".repeat(i*3) + "\\"
                    + " ".repeat(WIDTH - i * 6 - 2) + "/" + "*".repeat(i*3));
                
                Thread.sleep(60);
            }

            // Play worm hole sound while we are falling through time and space.
            world.emit(new EventSound(SoundType.WormHole));
            world.getStoryFlags().completeSideQuest(Quest.WormHole);

            for (int i=0;i<25*16;i++) {
                var out = "";
                for (int j=0;j<WIDTH;j++) {
                    out += random.nextInt(8) == 0 ? "*" : " ";
                }

                io.println(out);
                Thread.sleep(40);
            }

            for (int i=5;i>0;i--) {
                io.println("*".repeat(i*3) + "/"
                    + " ".repeat(WIDTH - i * 6 - 2) + "\\" + "*".repeat(i*3));
                
                Thread.sleep(60);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            io.println("There was a disruption when travelling.");
        }
        
        io.print("\n");
        
        // Pick a random location and put the entering entity in it.
        String location = locations[random.nextInt(locations.length)];
        Room target = this.getWorld().getRoom(location);
        entity.setLocation(target);

        // If it was the player, clear their walk history.
        if (entity instanceof EntityPlayer) {
            ((EntityPlayer) entity).clearHistory();
        }
    }
}
