package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.actions.IUseable;
import uk.insrt.coursework.zuul.world.World;

/**
 * Command which allows the Player to use an Entity.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class CommandUse extends Command {
    public CommandUse() {
        super("use <selectors.something>", "<commands.use.usage>",
            new Pattern[] {
                Pattern.compile("^use(?:\\s+(?<entity>[\\w\\s]+))*")
                // use, use <entity>
            });
    }

    @Override
    public boolean run(World world, Arguments args) {
        Entity entity = this.findEntity(world, Command.FILTER_ALL, args, "<commands.use.nothing_specified>");
        if (entity != null) {
            if (entity instanceof IUseable) {
                ((IUseable) entity).use(world.getPlayer());
            } else {
                world.getIO().println("<commands.use.denied> " + entity.getHighlightedName() + ".");
            }
        }

        return false;
    }
}
