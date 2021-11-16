package uk.insrt.coursework.zuul.commands;

import java.util.HashMap;
import java.util.List;

import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.World;

public class CommandManager {
    private HashMap<String, Command> commands = new HashMap<>();

    public CommandManager() {
        this.initialiseCommands();
    }

    private void initialiseCommands() {
        this.commands.put("go", new Command() {
            public boolean run(World world, List<String> arguments) {
                Direction direction = Direction.fromString(arguments.remove(0));
                if (direction == null) {
                    System.out.println("Where are you going?");
                    return false;
                }

                if (world.getPlayer().go(direction)) {
                    System.out.println("You cannot go this way.");
                }

                return false;
            }
        });

        this.commands.put("quit", new Command() {
            public boolean run(World world, List<String> arguments) {
                return true;
            }
        });
    }

    public Command getCommand(String cmd) {
        return this.commands.get(cmd);
    }

    public void getFuzzyCommand() {
        //
    }
}
