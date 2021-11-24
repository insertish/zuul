package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.world.World;

public class CommandQuit extends Command {
    public CommandQuit() {
        super("quit", "quit the game",
            new Pattern[] {
                Pattern.compile("^quit(?!\\w)"),
            });
    }

    @Override
    public boolean run(World world, Arguments arguments) {
        return true;
    }
}
