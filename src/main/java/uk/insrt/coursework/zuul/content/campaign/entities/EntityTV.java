package uk.insrt.coursework.zuul.content.campaign.entities;

import uk.insrt.coursework.zuul.dialogue.Dialogue;
import uk.insrt.coursework.zuul.dialogue.DialogueNode;
import uk.insrt.coursework.zuul.dialogue.DialogueOption;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.EntityWithDialogue;
import uk.insrt.coursework.zuul.entities.actions.IUseable;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

public class EntityTV extends EntityWithDialogue<EntityTV.State> implements IUseable {
    public EntityTV(World world, Location location) {
        super(world, location, 40, State.FirstOn);
    }

    protected enum State {
        FirstOn,
        Menu,
    }

    @Override
    public void use(Entity target) {
        this.dialogue.run(this.getWorld().getIO());
    }

    @Override
    public void setupDialogue(Dialogue<State> dialogue) {
        dialogue.addPart(State.FirstOn, new DialogueNode<State>("sus!")
            .addOption(new DialogueOption<State>("Turn the TV off.", State.FirstOn).mustExit()));
    }

    @Override
    public String[] getAliases() {
        return new String[] { "tv", "television" };
    }

    @Override
    public String describe() {
        return "LG 55NANO966PA 55\" Super UHD 8K HDR Smart LED TV";
    }
}
