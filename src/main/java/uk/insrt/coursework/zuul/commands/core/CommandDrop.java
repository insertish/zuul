package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.Inventory;
import uk.insrt.coursework.zuul.io.Ansi;
import uk.insrt.coursework.zuul.io.IOSystem;
import uk.insrt.coursework.zuul.world.World;

public class CommandDrop extends Command {
    public CommandDrop() {
        super("drop <item>", "drop an item from your bag",
            new Pattern[] {
                Pattern.compile("^drop(?:\\s+(?<entity>[\\w\\s]+))*")
            });
    }

    @Override
    public boolean run(World world, Arguments args) {
        Entity entity = this.findEntity(world, Command.FILTER_INVENTORY, args, "What do you want to drop?");
        if (entity != null) {
            world.getIO().println("You drop " + Ansi.BackgroundWhite + Ansi.Black + entity.getName() + Ansi.Reset + " out of your bag!");
            entity.setLocation(world.getPlayer().getRoom());
        }

        return false;
    }
}
