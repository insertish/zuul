package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.world.World;

public class CommandUse extends Command {
    public CommandUse() {
        super("use <something>: use an object or something in your inventory",
            new Pattern[] {
                Pattern.compile("^use\\s+(?<entity>[\\w\\s]+)"),
                Pattern.compile("^use")
            });
    }

    @Override
    public boolean run(World world, Arguments arguments) {
        String name = arguments.group("entity");
        if (name == null) {
            System.out.println("Use what?");
            return false;
        }

        Entity entity = world.findEntity(name);
        if (entity != null) {
            if (!entity.use(world.getPlayer())) {
                System.out.println("You cannot use " + name + ".");
            }

            return false;
        }

        System.out.println("You look around for " + name + " but can't find anything.");
        return false;
    }
}
