package uk.insrt.coursework.zuul.entities.actions;

import uk.insrt.coursework.zuul.entities.Entity;

public interface IUseable {
    /**
     * Use this entity.
     * @param target The Entity taking this entity.
     * @return Whether this entity can be used.
     */
    public void use(Entity target);
}
