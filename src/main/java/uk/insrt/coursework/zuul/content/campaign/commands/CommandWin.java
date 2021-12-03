package uk.insrt.coursework.zuul.content.campaign.commands;

import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.Arguments;
import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.content.campaign.CampaignWorld;
import uk.insrt.coursework.zuul.content.campaign.StoryFlags.Stage;
import uk.insrt.coursework.zuul.world.World;

/**
 * This command is unlocked after the player completes the final mission.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class CommandWin extends Command {
    public CommandWin() {
        super("win", "<commands.win.usage>",
            new Pattern[] {
                Pattern.compile("^win(?!\\w)"),
                // win
            });
    }

    @Override
    public boolean run(World world, Arguments args) {
        var io = world.getIO();
        var w = (CampaignWorld) world;
        var flags = w.getStoryFlags();
        if (flags.getStage() != Stage.End) return false;

        io.println("<commands.win.conclusion>\n<commands.win.stats>\n"
            + "<commands.win.total_ticks>" + flags.getTicks() + "\n"
            + "<commands.win.total_time>" + flags.prettyPrintTimeElapsed() + "\n"
            + "<commands.win.sidequests_complete>"
            + flags.getCompletedQuests() + " / " + flags.getTotalQuests()
            + "\n\n<commands.win.press_enter_key>");

        io.readLine();
        return true;
    }

    @Override
    public boolean isVisible() {
        return false;
    }
}
