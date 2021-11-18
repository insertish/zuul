package uk.insrt.coursework.zuul.commands;

import java.util.regex.Matcher;

import uk.insrt.coursework.zuul.world.Direction;

public class Arguments {
    private Matcher matcher;

    public Arguments(Matcher matcher) {
        this.matcher = matcher;
    }

    public String group(String group) {
        try {
            return this.matcher.group(group);
        } catch (Exception e) {
            return null;
        }
    }

    public Direction direction() {
        return Direction.fromString(this.group("direction"));
    }
}
