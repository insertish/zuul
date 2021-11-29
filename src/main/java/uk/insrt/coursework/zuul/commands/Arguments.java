package uk.insrt.coursework.zuul.commands;

import java.util.regex.Matcher;

import uk.insrt.coursework.zuul.world.Direction;

/**
 * Wrapper around regex Matcher for deriving the values of given arguments in commands.
 */
public class Arguments {
    private Matcher matcher;

    /**
     * Construct a new Arguments wrapper.
     * @param matcher Regex Matcher
     */
    public Arguments(Matcher matcher) {
        this.matcher = matcher;
    }

    /**
     * Take a named group from the Matcher.
     * We assume that the provided Regex doesn't match the group if it's empty.
     * @param group Named group
     * @return String value of named group or null if it doesn't exist
     */
    public String group(String group) {
        // We ignore and return null on error as a bit of convenience.
        // This may not be best practice but it is justified by the fact
        // that it avoids an incredible amount of boilerplate further up
        // the chain, and in my opinion that's worth this design decision.
        try {
            return this.matcher.group(group);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Check whether this named group was matched.
     * @param group Named group
     * @return Whether this named group was matched
     */
    public boolean has(String group) {
        return this.group(group) != null;
    }

    /**
     * Get the provided Direction.
     * @return Parsed Direction value
     */
    public Direction direction() {
        return Direction.fromString(this.group("direction"));
    }
}
