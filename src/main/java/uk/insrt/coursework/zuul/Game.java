package uk.insrt.coursework.zuul;

import java.util.Scanner;

import uk.insrt.coursework.zuul.commands.CommandManager;
import uk.insrt.coursework.zuul.content.campaign.CampaignWorld;
import uk.insrt.coursework.zuul.events.EventProcessCommand;
import uk.insrt.coursework.zuul.events.EventTick;
import uk.insrt.coursework.zuul.world.World;

public class Game {
    private World world;
    private CommandManager commands;
    
    private Scanner reader;

    public static void main(String[] args) {
        new Game().start();
    }

    public Game() {
        this.world = new CampaignWorld();
        this.commands = new CommandManager();
        this.reader = new Scanner(System.in);
    }

    public void start() {
        this.world.spawnPlayer();

        while (true) {
            System.out.print("\n$ ");
            String input = this.reader.nextLine().toLowerCase();
            System.out.print("\n----\n\n");

            EventProcessCommand event = new EventProcessCommand(input);
            this.world.emit(event);

            if (this.commands.runCommand(this.world, event.getCommand())) {
                break;
            }

            this.world.emit(new EventTick());
        }

        System.out.println("you were game ended");
    }
}
