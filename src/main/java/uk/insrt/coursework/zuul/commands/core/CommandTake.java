package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.Inventory;
import uk.insrt.coursework.zuul.io.IOSystem;
import uk.insrt.coursework.zuul.util.Search;
import uk.insrt.coursework.zuul.world.World;

/**
 * Command which allows the Player to take an Entity and put it in their Inventory.
 * They may also take these Entities from other Entity Inventories.
 */
public class CommandTake extends Command {
    public CommandTake() {
        super("take <selectors.something> [from <selectors.someone>]", "<commands.take.usage>",
            new Pattern[] {
                Pattern.compile("^take\\s+(?<entity>[\\w\\s]+)\\s+from\\s+(?<other>[\\w\\s]+)"),
                Pattern.compile("^take(?:\\s+(?<entity>[\\w\\s]+))*")
                // take, take <item>, take <item> from <entity>
            });
    }

    @Override
    public boolean run(World world, Arguments args) {
        IOSystem io = world.getIO();
        Entity player = world.getPlayer();
        Inventory target = player.getInventory();

        // Detect if we are taking from another entity, in that case run different logic.
        if (args.has("other")) {
            String name = args.group("entity");
            Entity other = this.findEntity(world, args, "other", "<commands.take.nothing_specified>");
            if (other == null) return false;

            Entity item = Search.findEntity(other.getInventory().getItems(), name, true);
            if (item == null) {
                io.println(other.getHighlightedName() + " <commands.take.entity_does_not_have_entity> " + name + "!");
                return false;
            }

            if (item.setLocation(target)) {
                io.println("<commands.take.took.1> " + item.getHighlightedName()
                    + " <commands.take.took.2> " + other.getHighlightedName()
                    + " <commands.take.took.3>.");
            } else {
                io.println("<commands.take.denied.1> " + item.getName()
                    + ", <commands.take.denied.2>.");
            }

            return false;
        }

        // Otherwise, look around the room and find something we can take.
        Entity entity = this.findEntity(world, args, "<commands.take.item_not_specified>");
        if (entity != null) {
            if (entity == player) {
                io.println("😳");
                return false;
            }

            if (entity.setLocation(target)) {
                io.println("<commands.take.took.1> " + entity.getHighlightedName()
                    + " <commands.take.took.3>.");
            } else {
                io.println("<commands.take.denied.1> " + entity.getHighlightedName()
                    + ", <commands.take.denied.2>.");
            }
        }

        return false;
    }
}
