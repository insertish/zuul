package uk.insrt.coursework.zuul.util;

import java.util.Arrays;

import uk.insrt.coursework.zuul.entities.Entity;

public class Search {
    public static Entity findEntity(Iterable<Entity> entities, String name, boolean fuzzy) {
        String normalised = name.toLowerCase();
        for (Entity entity : entities) {
            String[] aliases = entity.getAliases();
            for (String alias : aliases) {
                if (fuzzy) {
                    if (Arrays.asList(normalised.split("\\s"))
                        .contains(alias)) {
                        return entity;
                    }
                } else if (normalised.equals(alias)) {
                    return entity;
                }
            }
        }

        return null;
    }
}
