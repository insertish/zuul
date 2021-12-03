package uk.insrt.coursework.zuul.content.campaign.rooms;

import uk.insrt.coursework.zuul.content.campaign.StoryFlags.Stage;
import uk.insrt.coursework.zuul.content.campaign.entities.EntityNpc;
import uk.insrt.coursework.zuul.content.campaign.events.EventGameStageChanged;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.events.IEventListener;
import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.World;

/**
 * One of the major connecting points between locations in the city.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class RoomStreet extends CampaignRoom implements IEventListener<EventGameStageChanged> {
    private Entity protestorsEntity;

    public RoomStreet(World world) {
        super(world, "Street");
    }
    
    @Override
    public String describe() {
        var world = this.getWorld();
        if (!world.hasVisited(this)) {
            return "<street.first_load>";
        }

        return "<street.enter>";
    }

    @Override
    protected void setupDirections() {
        World world = this.getWorld();
        this.setAdjacent(Direction.SOUTH, world.getRoom("Apartments: Reception"));
        this.setAdjacent(Direction.EAST, world.getRoom("City Centre"));
        this.setAdjacent(Direction.NORTH, world.getRoom("Shop"));
        this.setAdjacent(Direction.WEST, world.getRoom("Medical Centre: Reception"));
    }

    @Override
    public boolean canLeave(Direction direction) {
        if (direction == Direction.WEST) {
            if (this.protestorsEntity.getRoom() == this) {
                this.getWorld()
                    .getIO()
                    .println("<street.protestors.blocking>");
                
                return false;
            }
        }

        return true;
    }

    @Override
    public void spawnEntities() {
        World world = this.getWorld();
        this.protestorsEntity = new EntityNpc(
            world,
            this.toLocation(),
            "npc_protestors",
            "<street.protestors.description>",
            new String[] { "protestors", "protestor" }
        );
        world.spawnEntity("npc_protestors", this.protestorsEntity);
    }

    @Override
    public void onEvent(EventGameStageChanged event) {
        // Remove the protestors if we are in Stealth chapter.
        if (event.getStage() == Stage.Stealth) {
            this.protestorsEntity.consume(false);
        } else {
            this.protestorsEntity.setLocation(this);
        }
    }
}
