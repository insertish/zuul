package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.World;

public class CommandGo extends Command {
    public CommandGo() {
        super("go <direction>: go in a certain direction",
            new Pattern[] {
                Pattern.compile("^go\\s+(?<direction>[\\w\\s]+)"),
                Pattern.compile("^go")
            });
    }

    @Override
    public boolean run(World world, Arguments arguments) {
        Direction direction = arguments.direction("Where are you going?");
        if (direction == null) return false;

        world.getPlayer().go(direction);
        return false;
    }
}
