package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.actions.ITalkwith;
import uk.insrt.coursework.zuul.world.World;

public class CommandTalk extends Command {
    public CommandTalk() {
        super("talk with <someone>", "start talking with someone",
            new Pattern[] {
                Pattern.compile("^talk with(?:\\s+(?<entity>[\\w\\s]+))*"),
                Pattern.compile("^talk")
            });
    }

    @Override
    public boolean run(World world, Arguments args) {
        Entity entity = this.findEntity(world, args, "What do you want to talk with?");
        if (entity != null) {
            if (entity instanceof ITalkwith) {
                ((ITalkwith) entity).talk();
            } else {
                world.getIO().println("You cannot talk with " + entity.getName() + ".");
            }
        }

        return false;
    }
}
