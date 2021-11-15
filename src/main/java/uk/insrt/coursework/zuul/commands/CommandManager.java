package uk.insrt.coursework.zuul.commands;

import java.util.HashMap;
import java.util.List;

import uk.insrt.coursework.zuul.Game;

public class CommandManager {
    private HashMap<String, Command> commands = new HashMap<>();

    public CommandManager() {
        this.initialiseCommands();
    }

    private void initialiseCommands() {
        this.commands.put("go", new Command() {
            public boolean run(Game game, List<String> arguments) {
                return false;
            }
        });

        this.commands.put("quit", new Command() {
            public boolean run(Game game, List<String> arguments) {
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
