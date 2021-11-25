package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.actions.IGiveable;
import uk.insrt.coursework.zuul.world.World;

public class CommandGive extends Command {
    public CommandGive() {
        super("give <something> to <someone>", "give something to someone",
            new Pattern[] {
                Pattern.compile("^(?:give|put)(?:\\s+(?<item>[\\w\\s]+)\\s+(?:to|in)\\s+(?<entity>[\\w\\s]+))*")
            });
    }

    @Override
    public boolean run(World world, Arguments args) {
        Entity item = this.findEntity(world, Command.FILTER_ALL, args, "item", "What do you want to give?");
        if (item == null) return false;

        Entity target = this.findEntity(world, args, "What / who are you putting this in?");
        if (target == null) return false;

        if (target instanceof IGiveable) {
            ((IGiveable) target).give(item);
        } else {
            world.getIO().println("Cannot give " + item.getHighlightedName()
                + " to " + target.getHighlightedName() + "!");
        }

        return false;
    }
}
