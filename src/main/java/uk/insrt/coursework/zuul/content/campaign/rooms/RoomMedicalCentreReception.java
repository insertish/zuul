package uk.insrt.coursework.zuul.content.campaign.rooms;

import uk.insrt.coursework.zuul.dialogue.Dialogue;
import uk.insrt.coursework.zuul.dialogue.DialogueNode;
import uk.insrt.coursework.zuul.dialogue.DialogueOption;
import uk.insrt.coursework.zuul.entities.EntityNPC;
import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

public class RoomMedicalCentreReception extends Room {
    public RoomMedicalCentreReception(World world) {
        super(world, "Medical Centre: Reception");
    }
    
    public String describe() {
        return "You're now at the Medical Centre's reception.";
    }

    protected void setupDirections() {
        World world = this.getWorld();
        this.setAdjacent(Direction.EAST, world.getRoom("Street"));
        this.setAdjacent(Direction.DOWN, world.getRoom("Medical Centre: Office"));
    }

    public boolean canLeave(Direction direction) {
        World world = this.getWorld();
        if (direction == Direction.DOWN) {
            if (world.getEntitiesInRoom(this)
                .contains(world.getEntity("guard1"))) {
                this.getWorld().getIO().println("There is security watching the stairs, there's no way to get past them.");
                return false;
            }
        }

        return true;
    }

    public static enum Test {
        Start
    }

    public void spawnEntities() {
        World world = this.getWorld();

        world.spawnEntity("guard1", new EntityNPC<Test>(world, this.toLocation(), Test.Start) {
            @Override
            public String[] getAliases() {
                return new String[] { "security guard", "guard" };
            }

            @Override
            public String describe() {
                return "guard";
            }

            @Override
            public void setupDialogue(Dialogue<Test> dialogue) {
                dialogue.addPart(Test.Start,
                    new DialogueNode<Test>("test")
                        .addOption(new DialogueOption<Test>(Test.Start, "test").mustExit()));
            }
        });
    }
}
