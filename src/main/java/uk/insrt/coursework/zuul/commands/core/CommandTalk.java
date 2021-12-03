package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.actions.ITalkwith;
import uk.insrt.coursework.zuul.world.World;

/**
 * Command which allows the Player to talk with other Entities.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class CommandTalk extends Command {
    public CommandTalk() {
        super("talk with <selectors.someone>", "<commands.talk.usage>",
            new Pattern[] {
                Pattern.compile("^talk(?:(?:\\s*with|to)*(?:\\s+(?<entity>[\\w\\s]+))*)*")
                // talk, talk with <entity>, talk to <entity>
            });
    }

    @Override
    public boolean run(World world, Arguments args) {
        Entity entity = this.findEntity(world, args, "<commands.talk.nothing_specified>");
        if (entity != null) {
            if (entity instanceof ITalkwith) {
                ((ITalkwith) entity).talk();
            } else {
                world.getIO().println("<commands.talk.denied> " + entity.getHighlightedName() + ".");
            }
        }

        return false;
    }
}
