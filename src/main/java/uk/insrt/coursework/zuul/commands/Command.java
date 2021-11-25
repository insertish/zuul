package uk.insrt.coursework.zuul.commands;

import java.util.List;
import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.Inventory;
import uk.insrt.coursework.zuul.util.Search;
import uk.insrt.coursework.zuul.world.World;

/**
 * Representation of an action which can be performed by the user.
 */
public abstract class Command {
    private Pattern[] patterns;
    private String syntax;
    private String usage;

    /**
     * Construct a new Command.
     * @param syntax Information about how to use the command
     * @param usage Information about what the command does
     * @param patterns Patterns to execute this command on
     */
    public Command(String syntax, String usage, Pattern[] patterns) {
        this.patterns = patterns;
        this.syntax = syntax;
        this.usage = usage;
    }

    /**
     * Get information about how to use the command.
     * @return String Information about how to use the command
     */
    public String getSyntax() {
        return this.syntax;
    }

    /**
     * Get information about what the command does.
     * @return String Information about what the command does
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

    public static final int FILTER_ROOM = 1;
    public static final int FILTER_INVENTORY = 2;
    public static final int FILTER_ALL = 3;

    public Entity findEntity(World world, int filter, Arguments args, String group, String failure) {
        String name = args.group(group);
        if (name == null) {
            world.getIO().println(failure);
            return null;
        }

        Entity player = world.getPlayer();
        Entity entity = null;
        if ((filter & FILTER_INVENTORY) == FILTER_INVENTORY) {
            Inventory inventory = player.getInventory();
            entity = Search.findEntity(inventory.getItems(), name, true);
        }
        
        if (entity == null
         && (filter & FILTER_ROOM) == FILTER_ROOM) {
            List<Entity> entities = world.getEntitiesInRoom(player.getRoom());
            entity = Search.findEntity(entities, name, true);
        }

        if (entity == null) {
            world.getIO().println("You look around for " + name + " but can't find anything.");
        }

        return entity;
    }

    public Entity findEntity(World world, Arguments args, String failure) {
        return this.findEntity(world, FILTER_ROOM, args, "entity", failure);
    }
}
