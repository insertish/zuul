package uk.insrt.coursework.zuul.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.world.World;

public class CommandBack extends Command {
    public CommandBack() {
        super("back: go back to the previous room",
            new Pattern[] {
                Pattern.compile("^back(?!\\w)"),
            });
    }

    @Override
    public boolean run(World world, Matcher matcher) {
        world.getPlayer().back();
        return false;
    }
}
