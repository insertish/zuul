package uk.insrt.coursework.zuul.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.world.World;

public abstract class Command {
    public abstract Pattern[] getPatterns();
    public abstract boolean run(World world, Matcher matcher);
}
