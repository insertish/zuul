package uk.insrt.coursework.zuul.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.World;

/**
 * Command handler which constructs, then resolves
 * and executes commands from an arbitrary input.
 */
public class CommandManager {
    private ArrayList<Command> commands = new ArrayList<>();

    /**
     * Construct a new CommandManager.
     * 
     * You should only need one present at any given time.
     */
    public CommandManager() {
        this.initialiseCommands();
    }

    /**
     * Initialise all the commands a player can execute.
     */
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
                    Pattern.compile("^pet\\s+(?<entity>[\\w\\s]+)"),
                    Pattern.compile("^pet(?:<entity>)")
                };
            }

            public boolean run(World world, Matcher matcher) {
                String name = matcher.group("entity");
                if (name == null) {
                    System.out.println("Pet what?");
                    return false;
                }

                // turn into method
                List<Entity> entities = world.getEntitiesInRoom(world.getPlayer().getRoom());
                for (Entity entity : entities) {
                    String[] aliases = entity.getAliases();
                    for (String alias : aliases) {
                        if (name.equalsIgnoreCase(alias)) {
                            if (!entity.pet()) {
                                System.out.println("You cannot pet " + alias + ".");
                            }

                            return false;
                        }
                    }
                }

                System.out.println("You look around for " + name + " but can't find anything.");
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

    /**
     * Interpret a given command and execute it within the scope of a given world.
     * @param world Current World object
     * @param cmd Arbitrary input to match against
     * @return Boolean indicating whether the game loop should exit.
     */
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
