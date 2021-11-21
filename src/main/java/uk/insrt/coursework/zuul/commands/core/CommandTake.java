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
    public boolean run(World world, Arguments arguments) {
        Entity entity = arguments.entity(world, "What do you want to take?");
        if (entity != null) {
            if (entity.take(world.getPlayer())) {
                System.out.println("You take " + entity.getName() + " and put it in your bag.");
            } else {
                System.out.println("You cannot take " + entity.getName() + ".");
            }
        }

        return false;
    }
}
