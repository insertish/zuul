package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.world.World;

/**
 * Command which allows the player to quit the game.
 */
public class CommandQuit extends Command {
    public CommandQuit() {
        super("quit", "<commands.quit>",
            new Pattern[] {
                Pattern.compile("^quit(?!\\w)"),
                // quit
            });
    }

    @Override
    public boolean run(World world, Arguments arguments) {
        // We return true from run() in order to tell the game loop to exit.
        return true;
    }
}
