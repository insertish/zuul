package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.World;

/**
 * Command which allows the Player to walk in a particular Direction.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class CommandGo extends Command {
    public CommandGo() {
        super("go <selectors.direction>", "<commands.go.usage>",
            new Pattern[] {
                Pattern.compile("^(?:go|walk)(?:\\s+(?<direction>[\\w\\s]+))*")
                // go, walk, go <direction>, walk <direction>
            });
    }

    @Override
    public boolean run(World world, Arguments arguments) {
        Direction direction = arguments.direction();
        if (direction == null) {
            world.getIO().println("<commands.go.nothing_specified>");
            return false;
        }

        world.getPlayer().go(direction);
        return false;
    }
}
