package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.Inventory;
import uk.insrt.coursework.zuul.io.Ansi;
import uk.insrt.coursework.zuul.io.IOSystem;
import uk.insrt.coursework.zuul.world.World;

/**
 * Command which allows the Player to look at their or another Entity's inventory.
 */
public class CommandBag extends Command {
    public CommandBag() {
        super("inventory [of <selectors.something>]", "<commands.bag.usage>",
            new Pattern[] {
                Pattern.compile("^(?:b(?:ag)*|inv(?:entory)*)(?:\\s+(?<entity>[\\w\\s]+))*"),
                // b, bag, inv, inventory, bag of <entity>, inventory of <entity>, (+2)
            });
    }

    @Override
    public boolean run(World world, Arguments arguments) {
        IOSystem io = world.getIO();

        // Figure out if we're checking our own inventory or another entity's inventory.
        Entity entity;
        boolean ours;
        if (arguments.has("entity")) {
            ours = false;
            entity = this.findEntity(world, arguments, "<commands.bag.cant_find>");
            if (entity == null) return false;
        } else {
            ours = true;
            entity = world.getPlayer();
        }
        
        // Get the selected entity's inventory and provide output if empty.
        Inventory inv = entity.getInventory();
        if (inv.getWeight() == 0) {
            if (ours) {
                io.println("<commands.bag.empty> <commands.bag.can_carry_kg> "
                    + inv.getMaxWeight() + " kg.");
            } else {
                io.println(entity.getHighlightedName() + " <commands.bag.entity_empty>.");
            }

            return false;
        }

        // Otherwise describe some statistics about the inventory.
        if (ours) {
            io.println("<commands.bag.are_carrying_kg> " + inv.getWeight()
                + " / " + inv.getMaxWeight() + " kg.\n<commands.bag.look_in_bag>:");
        } else {
            io.println(entity.getHighlightedName() + " <commands.bag.entity_appears_to_have>:");
        }

        // Describe all the items in this inventory we are currently looking at.
        for (Entity item : inv.getItems()) {
            io.println("- " + Ansi.Yellow + item.getWeight() + " kg"
                + Ansi.Reset + " " + item.describe()
                + " (" + item.getHighlightedName() + ")");
        }

        return false;
    }
}
