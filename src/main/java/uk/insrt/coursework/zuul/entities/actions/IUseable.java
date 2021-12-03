package uk.insrt.coursework.zuul.entities.actions;

import uk.insrt.coursework.zuul.entities.Entity;

/**
 * Interface implemented to provide the
 * ability for an Entity to be used by the player.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public interface IUseable {
    /**
     * Use this entity.
     * @param target The Entity taking this entity.
     */
    public void use(Entity target);
}
