package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.world.World;

public class CommandGive extends Command {
    public CommandGive() {
        super("give <something> to <someone>: give something to someone",
            new Pattern[] {
                Pattern.compile("^give(?:\\s+(?<entity>[\\w\\s]+))*")
            });
    }

    @Override
    public boolean run(World world, Arguments args) {
        Entity entity = this.findEntity(world, args, "What do you want to take?");
        if (entity != null) {
            if (entity.take(world.getPlayer())) {
                world.getIO().println("You take " + entity.getName() + " and put it in your bag.");
            } else {
                world.getIO().println("You cannot take " + entity.getName() + ".");
            }
        }

        return false;
    }
}
