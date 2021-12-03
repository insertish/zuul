package uk.insrt.coursework.zuul.util;

import java.util.Arrays;

import uk.insrt.coursework.zuul.entities.Entity;

/**
 * Utilities for searching through data structures related to the game
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class Search {
    /**
     * Find an Entity within an Iterable of entities given certain parameters.
     * @param entities Iterable of Entities which we search through
     * @param name Query which we are matching for
     * @param fuzzy Whether to match whether the alias contains this name in contrast to just doing exact matching
     * @return The Entity if it is found or null
     */
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
