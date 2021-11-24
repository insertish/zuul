package uk.insrt.coursework.zuul.commands.core;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.world.World;

public class CommandBack extends Command {
    public CommandBack() {
        super("back", "go back to the previous room",
            new Pattern[] {
                Pattern.compile("^back(?!\\w)"),
            });
    }

    @Override
    public boolean run(World world, Arguments arguments) {
        world.getPlayer().back();
        return false;
    }
}
