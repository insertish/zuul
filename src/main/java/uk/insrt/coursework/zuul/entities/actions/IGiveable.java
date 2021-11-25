package uk.insrt.coursework.zuul.entities.actions;

import uk.insrt.coursework.zuul.entities.Entity;

public interface IGiveable {
    /**
     * Give this entity another entity.
     * @param target Item being given
     */
    public void give(Entity item);
}
