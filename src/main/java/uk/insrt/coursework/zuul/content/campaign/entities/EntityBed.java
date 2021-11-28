package uk.insrt.coursework.zuul.content.campaign.entities;

import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.EntityObject;
import uk.insrt.coursework.zuul.entities.actions.IUseable;
import uk.insrt.coursework.zuul.events.world.EventTick;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

public class EntityBed extends EntityObject implements IUseable {
    public EntityBed(World world, Location location) {
        super(world, location, 80, new String[] { "bed" }, "<entities.bed.description>");
    }

    public void use(Entity target) {
        // for example, we could move the world forwards by 20 ticks
        for (int i=0;i<20;i++) {
            world.emit(new EventTick());
        }

        this.world.getIO().println("<entities.bed.use>");
    }
}
