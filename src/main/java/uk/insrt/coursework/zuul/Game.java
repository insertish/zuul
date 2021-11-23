package uk.insrt.coursework.zuul;

import uk.insrt.coursework.zuul.commands.CommandManager;
import uk.insrt.coursework.zuul.content.campaign.CampaignWorld;
import uk.insrt.coursework.zuul.events.EventProcessCommand;
import uk.insrt.coursework.zuul.events.EventTick;
import uk.insrt.coursework.zuul.io.IOSystem;
import uk.insrt.coursework.zuul.ui.TerminalEmulator;
import uk.insrt.coursework.zuul.world.World;

public class Game {
    private World world;
    private IOSystem io;
    private CommandManager commands;

    public static void main(String[] args) {
        new Game();
    }

    public Game() {
        // this.io = new StandardIO();
        this.io = new TerminalEmulator();

        /* for (int i=0;i<25;i++) {
            this.io.print(i + ":" + " ".repeat(i - 9 > 0 ? 1 : 2) + ".".repeat(i == 24 ? 80-5 : 80-4));
        }
        this.io.readLine(); */

        this.world = new CampaignWorld(io);
        this.commands = new CommandManager();
        this.start();
    }

    public void start() {
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
