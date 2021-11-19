package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.actions.IPettable;
import uk.insrt.coursework.zuul.world.World;

public class CommandPet extends Command {
    public CommandPet() {
        super("pet <something>: pet something in current room",
            new Pattern[] {
                Pattern.compile("^pet\\s+(?<entity>[\\w\\s]+)"),
                Pattern.compile("^pet")
            });
    }

    @Override
    public boolean run(World world, Arguments arguments) {
        Entity entity = arguments.entity(world, "What are you trying to pet?");
        if (entity != null) {
            if (entity instanceof IPettable) {
                ((IPettable) entity).pet();
            } else {
                System.out.println("You cannot use " + entity.getName() + ".");
            }
        }

        return false;
    }
}
