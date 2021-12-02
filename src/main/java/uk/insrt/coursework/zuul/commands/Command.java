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
     * Check whether this command is visible to the player in the help menu.
     * @return True if visible
     */
    public boolean isVisible() {
        return true;
    }

    /**
     * Run this command within the scope of a world and with any parsed arguments.
     * @param world Current World object
     * @param args Arguments passed into command
     * @return Boolean indicating whether the game loop should exit.
     */
    public abstract boolean run(World world, Arguments args);

    /**
     * Filter entities by those that are in the current room.
     */
    public static final int FILTER_ROOM = 1;

    /**
     * Filter entities by those that are in the player's inventory.
     */
    public static final int FILTER_INVENTORY = 2;

    /**
     * Don't filter entities and instead search through both the current room and the player's inventory.
     */
    public static final int FILTER_ALL = FILTER_ROOM + FILTER_INVENTORY;

    /**
     * Given a World and filter, use the provided Arguments and the relevant group to find an entity.
     * If an Entity is not found or not provided, the appropriate error is displayed to the player.
     * @param world World to look for the Entity within
     * @param filter Integer value which represents the filtering, specify one of: {@link #FILTER_ROOM}, {@link #FILTER_INVENTORY}, {@link #FILTER_ALL}
     * @param args Arguments object to pull information out of
     * @param group Group we should pull the Entity query out of
     * @param failure Failure message if an Entity is not specified
     * @return An Entity if one is found, or null if one isn't.
     */
    public Entity findEntity(World world, int filter, Arguments args, String group, String failure) {
        String name = args.group(group);
        if (name == null) {
            world.getIO().println(failure);
            return null;
        }

        // Search the inventory first.
        Entity player = world.getPlayer();
        Entity entity = null;
        if ((filter & FILTER_INVENTORY) == FILTER_INVENTORY) {
            Inventory inventory = player.getInventory();
            entity = Search.findEntity(inventory.getItems(), name, true);
        }
        
        // If we haven't found an entity yet, search the room.
        if (entity == null
         && (filter & FILTER_ROOM) == FILTER_ROOM) {
            List<Entity> entities = world.getEntitiesInRoom(player.getRoom());
            entity = Search.findEntity(entities, name, true);
        }

        if (entity == null) {
            world.getIO().println("<selectors.cant_find.1> " + name + " <selectors.cant_find.2>.");
        }

        return entity;
    }

    /**
     * Given a World and filter, use the provided Arguments and using the group "entity" to find an entity.
     * If an Entity is not found or not provided, the appropriate error is displayed to the player.
     * @param world World to look for the Entity within
     * @param filter Integer value which represents the filtering, specify one of: {@link #FILTER_ROOM}, {@link #FILTER_INVENTORY}, {@link #FILTER_ALL}
     * @param args Arguments object to pull information out of
     * @param failure Failure message if an Entity is not specified
     * @return An Entity if one is found, or null if one isn't.
     */
    public Entity findEntity(World world, int filter, Arguments args, String failure) {
        return this.findEntity(world, filter, args, "entity", failure);
    }

    /**
     * Given a World and using the {@link #FILTER_ROOM} filter, use the provided Arguments and using the group "entity" to find an entity.
     * If an Entity is not found or not provided, the appropriate error is displayed to the player.
     * @param world World to look for the Entity within
     * @param args Arguments object to pull information out of
     * @param failure Failure message if an Entity is not specified
     * @return An Entity if one is found, or null if one isn't.
     */
    public Entity findEntity(World world, Arguments args, String failure) {
        return this.findEntity(world, FILTER_ROOM, args, failure);
    }

    /**
     * Given a World and using the {@link #FILTER_ROOM} filter, use the provided Arguments and the relevant group to find an entity.
     * If an Entity is not found or not provided, the appropriate error is displayed to the player.
     * @param world World to look for the Entity within
     * @param args Arguments object to pull information out of
     * @param group Group we should pull the Entity query out of
     * @param failure Failure message if an Entity is not specified
     * @return An Entity if one is found, or null if one isn't.
     */
    public Entity findEntity(World world, Arguments args, String group, String failure) {
        return this.findEntity(world, FILTER_ROOM, args, group, failure);
    }
}
