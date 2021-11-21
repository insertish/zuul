package uk.insrt.coursework.zuul;

import uk.insrt.coursework.zuul.commands.CommandManager;
import uk.insrt.coursework.zuul.content.campaign.CampaignWorld;
import uk.insrt.coursework.zuul.events.EventProcessCommand;
import uk.insrt.coursework.zuul.events.EventTick;
import uk.insrt.coursework.zuul.io.IOSystem;
import uk.insrt.coursework.zuul.io.StandardIO;
import uk.insrt.coursework.zuul.world.World;

public class Game {
    private World world;
    private IOSystem io;
    private CommandManager commands;

    public static void main(String[] args) {
        new Game().start();
    }

    public Game() {
        this.io = new StandardIO();
        // this.io = new TerminalEmulator();
        this.world = new CampaignWorld(io);
        this.commands = new CommandManager();
    }

    public void start() {
        this.world.spawnPlayer();

        while (true) {
            this.io.print("\n$ ");
            String input = this.io.readLine().toLowerCase();
            this.io.print("\n----\n\n");

            EventProcessCommand event = new EventProcessCommand(input);
            this.world.emit(event);

            if (this.commands.runCommand(this.world, event.getCommand())) {
                break;
            }

            this.world.emit(new EventTick());
        }

        this.io.println("you were game ended");
    }
}
