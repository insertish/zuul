package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.Inventory;
import uk.insrt.coursework.zuul.io.Ansi;
import uk.insrt.coursework.zuul.io.IOSystem;
import uk.insrt.coursework.zuul.world.World;

public class CommandBag extends Command {
    public CommandBag() {
        super("inventory [of <something>]", "look inside your bag or at something's inventory",
            new Pattern[] {
                Pattern.compile("^(?:b(?:ag)*|inv(?:entory)*)(?:\\s+(?<entity>[\\w\\s]+))*"),
                // b, bag, inv, inventory, bag of <entity>, inventory of <entity>, (+2)
            });
    }

    @Override
    public boolean run(World world, Arguments arguments) {
        IOSystem io = world.getIO();

        Entity entity;
        boolean ours;
        if (arguments.has("entity")) {
            ours = false;
            entity = this.findEntity(world, arguments, "Can't find what you want to look at.");
            if (entity == null) return false;
        } else {
            ours = true;
            entity = world.getPlayer();
        }
        
        Inventory inv = entity.getInventory();
        if (inv.getWeight() == 0) {
            if (ours) {
                io.println("Your bag is empty! You can carry " + inv.getMaxWeight() + " kg.");
            } else {
                io.println(entity.getHighlightedName() + " doesn't appear to have anything.");
            }

            return false;
        }

        if (ours) {
            io.println("You are carrying " + inv.getWeight() + " / " + inv.getMaxWeight() + " kg.");
            io.println("You look in your bag to see:");
        } else {
            io.println(entity.getHighlightedName() + " appears to have:");
        }

        for (Entity item : inv.getItems()) {
            io.println("- " + Ansi.Yellow + item.getWeight() + " kg"
                + Ansi.Reset + " " + item.describe()
                + " (" + item.getHighlightedName() + ")");
        }

        return false;
    }
}
