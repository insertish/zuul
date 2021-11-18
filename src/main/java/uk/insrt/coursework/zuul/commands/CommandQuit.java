package uk.insrt.coursework.zuul.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.world.World;

public class CommandQuit extends Command {
    public CommandQuit() {
        super("quit: quit the game",
            new Pattern[] {
                Pattern.compile("^quit(?!\\w)"),
            });
    }

    @Override
    public boolean run(World world, Matcher matcher) {
        return true;
    }
}
