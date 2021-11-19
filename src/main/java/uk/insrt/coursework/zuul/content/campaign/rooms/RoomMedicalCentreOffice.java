package uk.insrt.coursework.zuul.content.campaign.rooms;

import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

public class RoomMedicalCentreOffice extends Room {
    public RoomMedicalCentreOffice(World world) {
        super(world, "Medical Centre: Office");
    }
    
    public String describe() {
        return "You find yourself at the Medical Centre's office.\nYou definitely shouldn't be here...";
    }

    protected void setupDirections() {
        this.setAdjacent(Direction.UP, this.getWorld().getRoom("Medical Centre: Reception"));
    }
}
