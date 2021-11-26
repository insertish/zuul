package uk.insrt.coursework.zuul;

import javax.swing.JOptionPane;

import uk.insrt.coursework.zuul.commands.CommandManager;
import uk.insrt.coursework.zuul.content.campaign.CampaignWorld;
import uk.insrt.coursework.zuul.content.campaign.commands.CommandMap;
import uk.insrt.coursework.zuul.events.world.EventProcessCommand;
import uk.insrt.coursework.zuul.events.world.EventTick;
import uk.insrt.coursework.zuul.io.LimitedIO;
import uk.insrt.coursework.zuul.io.IOSystem;
import uk.insrt.coursework.zuul.io.StandardIO;
import uk.insrt.coursework.zuul.ui.EventDraw;
import uk.insrt.coursework.zuul.ui.TerminalEmulator;
import uk.insrt.coursework.zuul.util.BlueJ;
import uk.insrt.coursework.zuul.world.World;

public class Game {
    public static final String GAME_NAME = "World of Deez";

    private World world;
    private IOSystem io;
    private CommandManager commands;

    public static void main(String[] args) {
        new Game().play();
    }

    public void play() {
        this.init();
        this.start();
    }

    private void init() {
        boolean inBlueJ = BlueJ.isRunningInBlueJ();
        int selection = JOptionPane.showConfirmDialog(null, "Play full experience?\nUses custom terminal emulator.\n(recommended option)", GAME_NAME, JOptionPane.YES_NO_OPTION);
        if (selection == 0) {
            if (inBlueJ) {
                // Fullscreen minimises itself immediately
                // when running from BlueJ, not sure what's
                // going on exactly, just disabling it in general.
                selection = 1;
            } else {
                selection = JOptionPane.showConfirmDialog(null, "Immersive mode?\nRuns emulator in fullscreen.\n(recommended option)", GAME_NAME, JOptionPane.YES_NO_OPTION);
            }

            this.io = new TerminalEmulator(selection == 0);
        } else {
            if (inBlueJ) {
                this.io = new LimitedIO();
            } else {
                this.io = new StandardIO();
            }
        }

        this.world = new CampaignWorld(this.io);
        this.commands = new CommandManager();

        // The map is only available in full experience mode.
        if (this.io instanceof TerminalEmulator) {
            CommandMap map = new CommandMap();
            this.commands.registerCommand(map);
            ((TerminalEmulator) this.io).getEventSystem().addListener(EventDraw.class, map);
        }
    }

    private void start() {
        this.world.spawnPlayer();

        while (true) {
            this.io.print("> ");
            String input = this.io.readLine().toLowerCase();

            EventProcessCommand event = new EventProcessCommand(input);
            this.world.emit(event);

            if (this.commands.runCommand(this.world, event.getCommand())) {
                break;
            }

            this.world.emit(new EventTick());
        }

        this.io.println("you were game ended");

        try {
            Thread.sleep(1000);
            this.io.dispose();
        } catch (Exception e) {}
    }
}
