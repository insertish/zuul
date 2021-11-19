package uk.insrt.coursework.zuul.commands;

import java.util.regex.Matcher;

import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.World;

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

    public Direction direction(String failure) {
        Direction direction = Direction.fromString(this.group("direction"));
        if (direction == null) System.out.println(failure);
        return direction;
    }

    public Entity entity(World world, String failure) {
        String name = this.group("entity");
        if (name == null) {
            System.out.println(failure);
            return null;
        }

        Entity entity = world.findEntity(name);
        if (entity == null) {
            System.out.println("You look around for " + name + " but can't find anything.");
            return null;
        }

        return entity;
    }
}
