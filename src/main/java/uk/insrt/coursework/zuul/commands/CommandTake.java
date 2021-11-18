package uk.insrt.coursework.zuul.commands;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.world.World;

public class CommandTake extends Command {
    public CommandTake() {
        super("take <something>: put something in your bag",
            new Pattern[] {
                Pattern.compile("^take\\s+(?<entity>[\\w\\s]+)"),
                Pattern.compile("^take")
            });
    }

    @Override
    public boolean run(World world, Arguments arguments) {
        String name = arguments.group("entity");
        if (name == null) {
            System.out.println("Take what?");
            return false;
        }

        Entity entity = world.findEntity(name);
        if (entity != null) {
            if (entity.take(world.getPlayer())) {
                System.out.println("You take " + name + " and put it in your bag.");
            } else {
                System.out.println("You cannot take " + name + ".");
            }

            return false;
        }

        System.out.println("You look around for " + name + " but can't find anything.");
        return false;
    }
}
