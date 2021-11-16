package uk.insrt.coursework.zuul.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.World;

public class CommandManager {
    private ArrayList<Command> commands = new ArrayList<>();

    public CommandManager() {
        this.initialiseCommands();
    }

    private void initialiseCommands() {
        this.commands.add(new Command() {
            public Pattern[] getPatterns() {
                return new Pattern[] {
                    Pattern.compile("^go\\s+(?<direction>\\w+)"),
                    Pattern.compile("^go(?:<direction>)")
                };
            }

            public boolean run(World world, Matcher matcher) {
                Direction direction = Direction.fromString(matcher.group("direction"));
                if (direction == null) {
                    System.out.println("Where are you going?");
                    return false;
                }

                world.getPlayer().go(direction);
                return false;
            }
        });

        this.commands.add(new Command() {
            public Pattern[] getPatterns() {
                return new Pattern[] {
                    Pattern.compile("^back(?!\\w)"),
                };
            }

            public boolean run(World world, Matcher matcher) {
                world.getPlayer().back();
                return false;
            }
        });

        this.commands.add(new Command() {
            public Pattern[] getPatterns() {
                return new Pattern[] {
                    Pattern.compile("^pet\\s+(?<entity>\\w+)"),
                    Pattern.compile("^pet(?:<entity>)")
                };
            }

            public boolean run(World world, Matcher matcher) {
                String name = matcher.group("entity");


                return false;
            }
        });

        this.commands.add(new Command() {
            public Pattern[] getPatterns() {
                return new Pattern[] {
                    Pattern.compile("^quit(?!\\w)"),
                };
            }

            public boolean run(World world, Matcher matcher) {
                return true;
            }
        });
    }

    public boolean runCommand(World world, String cmd) {
        for (Command command : this.commands) {
            for (Pattern pattern : command.getPatterns()) {
                Matcher matcher = pattern.matcher(cmd);
                if (matcher.find()) {
                    return command.run(world, matcher);
                }
            }
        }

        System.out.println("Not sure what you're trying to do.");
        return false;
    }
}
