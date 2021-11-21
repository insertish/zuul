package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.world.World;

public class CommandTake extends Command {
    public CommandTake() {
        super("take <something>: put something in your bag",
            new Pattern[] {
                Pattern.compile("^take(?:\\s+(?<entity>[\\w\\s]+))*")
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
