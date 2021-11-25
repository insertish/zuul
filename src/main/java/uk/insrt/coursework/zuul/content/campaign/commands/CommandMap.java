package uk.insrt.coursework.zuul.content.campaign.commands;

import java.awt.Image;
import java.io.InputStream;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.content.campaign.CampaignWorld;
import uk.insrt.coursework.zuul.events.IEventListener;
import uk.insrt.coursework.zuul.io.IOSystem;
import uk.insrt.coursework.zuul.ui.EventDraw;
import uk.insrt.coursework.zuul.world.World;

public class CommandMap extends Command implements IEventListener<EventDraw> {
    private boolean visible;
    private Image image;

    public CommandMap() {
        super("map", "show the world map",
            new Pattern[] {
                Pattern.compile("^(?:m(?:ap)*)(?!\\w)")
                // m, map
            });
        
        this.visible = false;

        try {
            InputStream stream = this.getClass().getResourceAsStream("/map/base.png");
            this.image = ImageIO.read(stream);
        } catch (Exception e) {}
    }

    @Override
    public boolean run(World world, Arguments arguments) {
        CampaignWorld campaignWorld = (CampaignWorld) world;
        IOSystem io = world.getIO();
        io.print("You have discovered " + campaignWorld.percentVisited()
            + "% of the world!" + "\n".repeat(24) + "Press any key to return.");

        this.visible = true;
        io.readLine();
        this.visible = false;
        return false;
    }

    @Override
    public void onEvent(EventDraw event) {
        if (!this.visible) return;

        float fw = event.getCharWidth();
        float fh = event.getCharHeight();

        var g = event.getGraphics();
        g.drawImage(
            this.image,
            Math.round(event.getOriginX()),
            Math.round(event.getOriginY() + fh),
            Math.round(fw * 80),
            Math.round(fh * 23),
            null
        );
    }
}
