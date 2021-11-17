package uk.insrt.coursework.zuul.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.world.World;

/**
 * Representation of an action which can be performed by the user.
 */
public abstract class Command {
    /**
     * Get all applicable patterns to match to execute this command.
     * @return Regex Pattern array
     */
    public abstract Pattern[] getPatterns();

    /**
     * Run this command within the scope of a world and with any parsed arguments.
     * @param world Current World object
     * @param matcher Matcher generated from Pattern(s) found from getPatterns()
     * @return Boolean indicating whether the game loop should exit.
     */
    public abstract boolean run(World world, Matcher matcher);
}
