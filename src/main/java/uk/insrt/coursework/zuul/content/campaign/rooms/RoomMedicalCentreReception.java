package uk.insrt.coursework.zuul.content.campaign.rooms;

import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.World;

public class RoomMedicalCentreReception extends CampaignRoom {
    public RoomMedicalCentreReception(World world) {
        super(world, "Medical Centre: Reception");
    }
    
    public String describe() {
        return "<medical_centre.enter>";
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
                this.getWorld()
                    .getIO()
                    .println("There is security watching the stairs, there's no way to get past them.");
                
                return false;
            }
        }

        return true;
    }

    public void spawnEntities() {
        World world = this.getWorld();

        /*world.spawnEntity("guard1", new EntityNpc(world, this.toLocation(), 75) {
            @Override
            public String[] getAliases() {
                return new String[] { "security", "guard" };
            }

            @Override
            public String describe() {
                return "guard";
            }

            @Override
            public void setupDialogue(Dialogue<String> dialogue) {
                dialogue.addPart(Test.Start,
                    new DialogueNode<Test>("test")
                        .addOption(new DialogueOption<Test>("simple", Test.Start).mustExit())
                        .addOption(new DialogueOption<Test>("complex", io -> {
                            io.println("here is can do a more complex interaction");
                            io.println("press enter to continue");
                            io.readLine();

                            return Test.Start;
                        })));
            }
        });*/
    }
}
