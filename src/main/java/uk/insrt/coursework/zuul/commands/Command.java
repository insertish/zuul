package uk.insrt.coursework.zuul.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * @param matcher Matcher generated from Pattern(s) found from getPatterns()
     * @return Boolean indicating whether the game loop should exit.
     */
    public abstract boolean run(World world, Matcher matcher);
}
