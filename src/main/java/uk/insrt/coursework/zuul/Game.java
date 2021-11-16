package uk.insrt.coursework.zuul;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import uk.insrt.coursework.zuul.commands.Command;
import uk.insrt.coursework.zuul.commands.CommandManager;
import uk.insrt.coursework.zuul.entities.Entity;
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

            String[] input = this.reader.nextLine().split("\\s+");
            List<String> args = new ArrayList<>(Arrays.asList(input));
            String cmd = args.remove(0);

            if (cmd == null) {
                continue;
            }

            Command command = this.commands.getCommand(cmd);
            if (command == null) {
                System.out.println("Not sure what you're trying to do.");

                try {
                    Thread.sleep(500);
                } catch (Exception e) { /* ignore exception */ }

                continue;
            }

            if (command.run(this.world, args)) {
                break;
            }
        }

        System.out.println("you were game ended");
    }

    private void outputState() {
        System.out.print("\n\n$ ");
    }
}
