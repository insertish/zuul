package uk.insrt.coursework.zuul.entities.actions;

import uk.insrt.coursework.zuul.entities.Entity;

/**
 * Interface implemented to provide the ability for
 * an Entity to have other Entities given to them.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public interface IGiveable {
    /**
     * Give this entity another entity.
     * @param item Item being given
     */
    public void give(Entity item);
}
