package uk.insrt.coursework.zuul.commands;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.entities.Entity;
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
        String name = arguments.group("entity");
        if (name == null) {
            System.out.println("Pet what?");
            return false;
        }

        Entity entity = world.findEntity(name);
        if (entity != null) {
            if (!entity.pet()) {
                System.out.println("You cannot pet " + name + ".");
            }

            return false;
        }

        System.out.println("You look around for " + name + " but can't find anything.");
        return false;
    }
}
