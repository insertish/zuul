package uk.insrt.coursework.zuul.commands;

import java.util.List;

import uk.insrt.coursework.zuul.Game;

public abstract class Command {
    public abstract boolean run(Game game, List<String> arguments);
}
