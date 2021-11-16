package uk.insrt.coursework.zuul;

import java.util.Scanner;

import uk.insrt.coursework.zuul.commands.CommandManager;
import uk.insrt.coursework.zuul.world.World;

public class Game {
    private World world;
    private CommandManager commands;
    
    private Scanner reader;

    public static void main(String[] args) {
        new Game().start();
    }

    public Game() {
        this.world = new World();
        this.commands = new CommandManager();
        this.reader = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            this.outputState();

            String input = this.reader.nextLine().toLowerCase();
            if (this.commands.runCommand(this.world, input)) {
                break;
            }
        }

        System.out.println("you were game ended");
    }

    private void outputState() {
        System.out.print("\n$ ");
    }
}
