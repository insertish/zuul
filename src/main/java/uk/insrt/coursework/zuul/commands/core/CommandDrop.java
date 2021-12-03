package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.io.Ansi;
import uk.insrt.coursework.zuul.world.World;

/**
 * Command which allows the Player to drop any item in their inventory.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class CommandDrop extends Command {
    public CommandDrop() {
        super("drop <selectors.item>", "<commands.drop.usage>",
            new Pattern[] {
                Pattern.compile("^(?:drop|place|put down)(?:\\s+(?<entity>[\\w\\s]+))*")
                // drop, place, put down, drop <item>, place <item>, put down <item>
            });
    }

    @Override
    public boolean run(World world, Arguments args) {
        // Find the given entity within our inventory and drop it if it's found.
        Entity entity = this.findEntity(world, Command.FILTER_INVENTORY, args, "<commands.drop.nothing_specified>");
        if (entity != null) {
            world.getIO().println("<commands.drop.dropped.1> " + Ansi.BackgroundWhite + Ansi.Black
                + entity.getName() + Ansi.Reset + " <commands.drop.dropped.2>!");
            entity.setLocation(world.getPlayer().getRoom());
        }

        return false;
    }
}
