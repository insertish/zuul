package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.events.world.EventEntityEnteredRoom;
import uk.insrt.coursework.zuul.world.World;

/**
 * Command which allows the player to reorient themselves in the world.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class CommandWhereAmI extends Command {
    public CommandWhereAmI() {
        super("where am i", "<commands.where_am_i>",
            new Pattern[] {
                Pattern.compile("^where(\\s+am\\s+(i(?!\\w))*)*"),
                // where am i
            });
    }

    @Override
    public boolean run(World world, Arguments arguments) {
        // We can just re-emit the enter room event to
        // trigger the room description logic to run again.
        world.emit(new EventEntityEnteredRoom(world.getPlayer()));
        return false;
    }
}
