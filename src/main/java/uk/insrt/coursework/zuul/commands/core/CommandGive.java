package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.EntityPlayer;
import uk.insrt.coursework.zuul.entities.actions.IGiveable;
import uk.insrt.coursework.zuul.world.World;

/**
 * Command which allows the player to give something to someone.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class CommandGive extends Command {
    public CommandGive() {
        super("give <selectors.something> to <selectors.someone>", "<commands.give.usage>",
            new Pattern[] {
                Pattern.compile("^(?:give|put)(?:\\s+(?<item>[\\w\\s]+)\\s+(?:to|in)\\s+(?<entity>[\\w\\s]+))*")
                // give, put, give <something> to <someone>, put <something> in <something>, (+2)
            });
    }

    @Override
    public boolean run(World world, Arguments args) {
        // Find the entity we want to give in the room or our inventory.
        Entity item = this.findEntity(world, Command.FILTER_ALL, args, "item", "<commands.give.nothing_specified>");
        if (item == null) return false;

        // Explicitly deny the player being given to anything, otherwise we will end up in an inventory.
        var io = world.getIO();
        if (item instanceof EntityPlayer) {
            io.println("<commands.give.denied_player>");
            return false;
        }

        // Find the target to give to.
        Entity target = this.findEntity(world, args, "<commands.give.no_target>");
        if (target == null) return false;

        // If the target entity is an IGiveable, check if they accept this item.
        if (target instanceof IGiveable) {
            ((IGiveable) target).give(item);
        } else {
            io.println("<commands.give.denied.1> " + item.getHighlightedName()
                + " <commands.give.denied.2> " + target.getHighlightedName() + "!");
        }

        return false;
    }
}
