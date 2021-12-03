package uk.insrt.coursework.zuul.content.campaign.rooms;

import uk.insrt.coursework.zuul.content.campaign.entities.EntityDocument;
import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.World;

/**
 * Private, usually inaccessible room within the Medical Centre complex.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class RoomMedicalCentreOffice extends CampaignRoom {
    public RoomMedicalCentreOffice(World world) {
        super(world, "Medical Centre: Office");
    }
    
    @Override
    public String describe() {
        return "<medical_centre_office.enter>";
    }

    @Override
    protected void setupDirections() {
        this.setAdjacent(Direction.UP, this.getWorld().getRoom("Medical Centre: Reception"));
    }

    @Override
    public void spawnEntities() {
        World world = this.getWorld();
        for (int i=1;i<=6;i++) {
            world.spawnEntity("doc" + i, new EntityDocument(world, this.toLocation(), i));
        }
    }
}
