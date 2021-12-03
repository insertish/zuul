package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.actions.IPettable;
import uk.insrt.coursework.zuul.world.World;

/**
 * Command which allows the player to pet another entity.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class CommandPet extends Command {
    public CommandPet() {
        super("pet <selectors.something>", "<commands.pet.usage>",
            new Pattern[] {
                Pattern.compile("^pet(?:\\s+(?<entity>[\\w\\s]+))*")
                // pet, pet <something>
            });
    }

    @Override
    public boolean run(World world, Arguments args) {
        // Scan the room for entities that have IPettable.
        Entity entity = this.findEntity(world, Command.FILTER_ALL, args, "<commands.pet.nothing_specified>");
        if (entity != null) {
            if (entity instanceof IPettable) {
                ((IPettable) entity).pet();
            } else {
                world.getIO().println("<commands.pet.denied> " + entity.getHighlightedName() + ".");
            }
        }

        return false;
    }
}
