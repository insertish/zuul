package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.Inventory;
import uk.insrt.coursework.zuul.io.IOSystem;
import uk.insrt.coursework.zuul.world.World;

public class CommandTake extends Command {
    public CommandTake() {
        super("take <something> [from <someone>]", "put something in your bag",
            new Pattern[] {
                Pattern.compile("^take(?:\\s+(?<entity>[\\w\\s]+)(?:\\s+from\\s+(?<other>[\\w\\s]+))*)*")
                // take, take <item>, take <item> from <entity>
            });
    }

    @Override
    public boolean run(World world, Arguments args) {
        Entity entity = this.findEntity(world, args, "What do you want to take?");
        if (entity != null) {
            IOSystem io = world.getIO();
            Entity player = world.getPlayer();
            if (entity == player) {
                io.println("😳");
                return false;
            }

            Inventory target = player.getInventory();
            if (entity.setLocation(target)) {
                io.println("You take " + entity.getName() + " and put it in your bag.");
            } else {
                io.println("You cannot take " + entity.getName() + ", it's too heavy to put in your bag.");
            }
        }

        return false;
    }
}
