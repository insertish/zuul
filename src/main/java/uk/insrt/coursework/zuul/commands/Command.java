package uk.insrt.coursework.zuul.commands;

import java.util.List;

import uk.insrt.coursework.zuul.world.World;

public abstract class Command {
    public abstract boolean run(World world, List<String> arguments);
}
