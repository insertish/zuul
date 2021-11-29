package uk.insrt.coursework.zuul.content.campaign.rooms;

import uk.insrt.coursework.zuul.content.campaign.entities.EntityNpc;
import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.World;

/**
 * Reception of the Medical Centre complex.
 */
public class RoomMedicalCentreReception extends CampaignRoom {
    public RoomMedicalCentreReception(World world) {
        super(world, "Medical Centre: Reception");
    }
    
    @Override
    public String describe() {
        return "<medical_centre.enter>";
    }

    @Override
    protected void setupDirections() {
        World world = this.getWorld();
        this.setAdjacent(Direction.EAST, world.getRoom("Street"));
        this.setAdjacent(Direction.DOWN, world.getRoom("Medical Centre: Office"));
    }

    @Override
    public boolean canLeave(Direction direction) {
        World world = this.getWorld();
        if (direction == Direction.DOWN) {
            if (world.getEntitiesInRoom(this)
                .contains(world.getEntity("npc_guard"))) {
                this.getWorld()
                    .getIO()
                    .println("There is security watching the stairs, there's no way to get past them.");
                
                return false;
            }
        }

        return true;
    }

    @Override
    public void spawnEntities() {
        World world = this.getWorld();
        world.spawnEntity("npc_guard",
            new EntityNpc(
                world,
                this.toLocation(),
                "npc_security_guard",
                "<medical_centre.guard.description>",
                new String[] { "guard", "security" }
            ));
    }
}
