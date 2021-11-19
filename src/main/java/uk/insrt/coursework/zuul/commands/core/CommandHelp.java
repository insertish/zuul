package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.commands.CommandManager;
import uk.insrt.coursework.zuul.world.World;

public class CommandHelp extends Command {
    private CommandManager commandManager;

    public CommandHelp(CommandManager commandManager) {
        super("help: show help menu",
            new Pattern[] {
                Pattern.compile("^help(?!\\w)")
            });
        
        this.commandManager = commandManager;
    }

    @Override
    public boolean run(World world, Arguments arguments) {
        System.out.println("You can run the following commands:");
        System.out.println(
            this.commandManager
                .getCommands()
                .stream()
                .filter(c -> !(c instanceof CommandHelp))
                .map(c -> "- " + c.getUsage())
                .collect(Collectors.joining("\n"))
        );

        return false;
    }
}
