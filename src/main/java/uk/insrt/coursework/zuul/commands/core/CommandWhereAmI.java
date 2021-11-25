package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.events.world.EventEntityEnteredRoom;
import uk.insrt.coursework.zuul.world.World;

public class CommandWhereAmI extends Command {
    public CommandWhereAmI() {
        super("where am i", "describe the current room again",
            new Pattern[] {
                Pattern.compile("^where am i(?!\\w)"),
                // where am i
            });
    }

    @Override
    public boolean run(World world, Arguments arguments) {
        world.emit(new EventEntityEnteredRoom(world.getPlayer()));
        return false;
    }
}
