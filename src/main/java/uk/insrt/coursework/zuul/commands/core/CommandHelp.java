package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.commands.CommandManager;
import uk.insrt.coursework.zuul.io.Ansi;
import uk.insrt.coursework.zuul.world.World;

/**
 * Command which allows the player to list all the available commands.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class CommandHelp extends Command {
    private CommandManager commandManager;

    public CommandHelp(CommandManager commandManager) {
        super("help", "<commands.help.usage>",
            new Pattern[] {
                Pattern.compile("^(?:h(?:elp)*)(?!\\w)")
                // h, help
            });
        
        this.commandManager = commandManager;
    }

    @Override
    public boolean run(World world, Arguments arguments) {
        // Describe all the commands the player can run.
        world.getIO()
            .println(
            "<commands.help.can_run>\n" +
            this.commandManager
                .getCommands()
                .stream()
                .filter(Command::isVisible)
                .map(c -> "- " + Ansi.BackgroundWhite + Ansi.Black
                    + c.getSyntax() + Ansi.Reset + ": " + c.getUsage())
                .collect(Collectors.joining("\n"))
        );

        return false;
    }

    @Override
    public boolean isVisible() {
        return false;
    }
}
