package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.Inventory;
import uk.insrt.coursework.zuul.io.IOSystem;
import uk.insrt.coursework.zuul.util.Search;
import uk.insrt.coursework.zuul.world.World;

public class CommandTake extends Command {
    public CommandTake() {
        super("take <something> [from <someone>]", "put something in your bag",
            new Pattern[] {
                Pattern.compile("^take\\s+(?<entity>[\\w\\s]+)\\s+from\\s+(?<other>[\\w\\s]+)"),
                Pattern.compile("^take(?:\\s+(?<entity>[\\w\\s]+))*")
                // take, take <item>, take <item> from <entity>
            });
    }

    @Override
    public boolean run(World world, Arguments args) {
        IOSystem io = world.getIO();
        Entity player = world.getPlayer();
        Inventory target = player.getInventory();

        if (args.has("other")) {
            String name = args.group("entity");
            Entity other = this.findEntity(world, args, "other", "From who?");
            if (other == null) return false;

            Entity item = Search.findEntity(other.getInventory().getItems(), name, true);
            if (item == null) {
                io.println(other.getHighlightedName() + " does not have " + name + "!");
                return false;
            }

            if (item.setLocation(target)) {
                io.println("You take " + item.getHighlightedName() + " from "
                    + other.getHighlightedName() + " and put it in your bag.");
            } else {
                io.println("You cannot take " + item.getName()
                    + ", it's too heavy to put in your bag.");
            }

            return false;
        }

        Entity entity = this.findEntity(world, args, "What do you want to take?");
        if (entity != null) {
            if (entity == player) {
                io.println("😳");
                return false;
            }

            if (entity.setLocation(target)) {
                io.println("You take " + entity.getHighlightedName()
                    + " and put it in your bag.");
            } else {
                io.println("You cannot take " + entity.getHighlightedName()
                    + ", it's too heavy to put in your bag.");
            }
        }

        return false;
    }
}
