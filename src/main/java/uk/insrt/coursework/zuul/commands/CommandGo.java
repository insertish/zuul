package uk.insrt.coursework.zuul.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.World;

public class CommandGo extends Command {
    public CommandGo() {
        super("go <direction>: go in a certain direction",
            new Pattern[] {
                Pattern.compile("^back(?!\\w)"),
            });
    }

    @Override
    public Pattern[] getPatterns() {
        return new Pattern[] {
            Pattern.compile("^go\\s+(?<direction>[\\w\\s]+)"),
            Pattern.compile("^go(?:<direction>)")
        };
    }

    @Override
    public boolean run(World world, Matcher matcher) {
        Direction direction = Direction.fromString(matcher.group("direction"));
        if (direction == null) {
            System.out.println("Where are you going?");
            return false;
        }

        world.getPlayer().go(direction);
        return false;
    }
}
