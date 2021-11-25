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
        super("bag", "look inside your bag",
            new Pattern[] {
                Pattern.compile("^b(?:ag)*(?!\\w)"),
            });
    }

    @Override
    public boolean run(World world, Arguments arguments) {
        IOSystem io = world.getIO();
        Inventory inv = world.getPlayer().getInventory();

        if (inv.getWeight() == 0) {
            io.println("Your bag is empty! You can carry " + inv.getMaxWeight() + " kg.");
            return false;
        }

        io.println("You are carrying " + inv.getWeight() + " / " + inv.getMaxWeight() + " kg.");
        io.println("You look in your bag to see:");
        for (Entity item : inv.getItems()) {
            io.println("- " + Ansi.Yellow + item.getWeight() + " kg"
                + Ansi.Reset + " " + item.describe()
                + " (" + item.getHighlightedName() + ")");
        }

        return false;
    }
}
