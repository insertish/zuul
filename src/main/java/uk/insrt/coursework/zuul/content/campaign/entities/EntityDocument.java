package uk.insrt.coursework.zuul.content.campaign.entities;

import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.EntityObject;
import uk.insrt.coursework.zuul.entities.actions.IUseable;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

/**
 * Documents which the player needs to find and take.
 */
public class EntityDocument extends EntityObject implements IUseable {
    private int count;

    /**
     * Construct a new EntityDocument
     * @param world World
     * @param location Location
     * @param count Document Id
     */
    public EntityDocument(World world, Location location, int count) {
        super(world, location, 10, "doc" + count, "<medical_centre_office.books." + count + ".title>");
        this.count = count;
    }

    @Override
    public void use(Entity target) {
        this.getWorld()
            .getIO()
            .println("<medical_centre_office.books." + count + ".contents>");
    }

    /**
     * Check whether these are the documents we want.
     * @return Whether we want this document
     */
    public boolean getIsValid() {
        switch (this.count) {
            case 2:
            case 4:
            case 5: return true;
        }

        return false;
    }
}
