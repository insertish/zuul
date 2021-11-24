package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.world.World;

public class CommandGive extends Command {
    public CommandGive() {
        super("give <something> to <someone>", "give something to someone",
            new Pattern[] {
                Pattern.compile("^give(?:\\s+(?<entity>[\\w\\s]+))*")
            });
    }

    @Override
    public boolean run(World world, Arguments args) {
        // 

        return false;
    }
}
