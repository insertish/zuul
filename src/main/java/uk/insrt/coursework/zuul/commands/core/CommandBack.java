package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.world.World;

/**
 * Command which allows the Player to walk back through the previous Rooms.
 */
public class CommandBack extends Command {
    public CommandBack() {
        super("back", "<commands.back>",
            new Pattern[] {
                Pattern.compile("^(?:(?:go|walk)\\s+)*back(?!\\w)"),
                // back, go back, walk back
            });
    }

    @Override
    public boolean run(World world, Arguments arguments) {
        // We call a specialised method on the player as we keep
        // track of visited rooms within the Player class itself.
        world.getPlayer().back();
        return false;
    }
}
