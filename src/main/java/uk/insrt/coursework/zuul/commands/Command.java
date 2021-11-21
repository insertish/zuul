package uk.insrt.coursework.zuul.commands;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.world.World;

/**
 * Representation of an action which can be performed by the user.
 */
public abstract class Command {
    private Pattern[] patterns;
    private String usage;

    /**
     * Construct a new Command.
     * @param usage Information about how to use the command
     * @param patterns Patterns to execute this command on
     */
    public Command(String usage, Pattern[] patterns) {
        this.patterns = patterns;
        this.usage = usage;
    }

    /**
     * Get information about how to use the command.
     * @return String Information about how to use the command
     */
    public String getUsage() {
        return this.usage;
    }

    /**
     * Get all applicable patterns to match to execute this command.
     * @return Regex Pattern array
     */
    public Pattern[] getPatterns() {
        return this.patterns;
    }

    /**
     * Run this command within the scope of a world and with any parsed arguments.
     * @param world Current World object
     * @param args Arguments passed into command
     * @return Boolean indicating whether the game loop should exit.
     */
    public abstract boolean run(World world, Arguments args);

    public Entity findEntity(World world, Arguments args, String failure) {
        String name = args.group("entity");
        if (name == null) {
            world.getIO().println(failure);
            return null;
        }

        Entity entity = world.findEntity(name, true);
        if (entity == null) {
            world.getIO().println("You look around for " + name + " but can't find anything.");
        }

        return entity;
    }
}
