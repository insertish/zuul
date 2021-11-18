package uk.insrt.coursework.zuul.commands;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import uk.insrt.coursework.zuul.world.World;

public class CommandHelp extends Command {
    private CommandManager commandManager;

    public CommandHelp(CommandManager commandManager) {
        super("help: show help menu",
            new Pattern[] {
                Pattern.compile("^back(?!\\w)"),
            });
        
        this.commandManager = commandManager;
    }

    @Override
    public Pattern[] getPatterns() {
        return new Pattern[] {
            Pattern.compile("^help(?!\\w)")
        };
    }

    @Override
    public boolean run(World world, Matcher matcher) {
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
