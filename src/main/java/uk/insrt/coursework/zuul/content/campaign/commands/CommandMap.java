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

/**
 * Command available for the terminal emulator which displays a graphical map.
 */
public class CommandMap extends Command implements IEventListener<EventDraw> {
    private boolean visible;
    private Image image;

    public CommandMap() {
        super("map", "<commands.map.usage>",
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
        io.print("<commands.map.discovered.1> " + campaignWorld.percentVisited()
            + "% <commands.map.discovered.2>!" + "\n".repeat(24) + "<commands.map.close>");

        // We make the map visible and block on user input,
        // Once the user interacts, the map is hidden again.
        this.visible = true;
        io.readLine();
        this.visible = false;
        return false;
    }

    @Override
    public void onEvent(EventDraw event) {
        if (!this.visible) return;

        // We are drawing the map from [0,1] to [80,24].
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
