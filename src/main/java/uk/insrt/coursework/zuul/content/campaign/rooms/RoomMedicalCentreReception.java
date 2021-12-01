package uk.insrt.coursework.zuul.content.campaign.rooms;

import uk.insrt.coursework.zuul.content.campaign.entities.EntityCouch;
import uk.insrt.coursework.zuul.content.campaign.entities.EntityNpc;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.events.world.EventEntityLeftRoom;
import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.World;

/**
 * Reception of the Medical Centre complex.
 */
public class RoomMedicalCentreReception extends CampaignRoom {
    private Entity guardEntity;
    private EntityCouch couchEntity;

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
        if (direction == Direction.DOWN) {
            if (this.guardEntity.getRoom() == this) {
                this.getWorld()
                    .getIO()
                    .println("<medical_centre.guard.blocking>");
                
                return false;
            }
        }

        return true;
    }

    @Override
    public void spawnEntities() {
        World world = this.getWorld();

        this.guardEntity = new EntityNpc(
            world,
            this.toLocation(),
            "npc_security_guard",
            "<medical_centre.guard.description>",
            new String[] { "guard", "security" }
        );
        world.spawnEntity("npc_guard", this.guardEntity);

        this.couchEntity = new EntityCouch(world, this.toLocation());
        world.spawnEntity("couch", this.couchEntity);
        world.getEventSystem().addListener(EventEntityLeftRoom.class, this.couchEntity);
    }

    public EntityCouch getCouch() {
        return this.couchEntity;
    }

    public Entity getGuard() {
        return this.guardEntity;
    }
}
