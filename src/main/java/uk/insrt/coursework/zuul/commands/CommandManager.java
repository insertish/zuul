package uk.insrt.coursework.zuul.commands;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.world.World;

/**
 * Command handler which constructs, then resolves
 * and executes commands from an arbitrary input.
 */
public class CommandManager {
    private ArrayList<Command> commands = new ArrayList<>();

    /**
     * Construct a new CommandManager.
     * 
     * You should only need one present at any given time.
     */
    public CommandManager() {
        this.initialiseCommands();
    }

    public void registerCommand(Command command) {
        this.commands.add(command);
    }

    public void registerCommands(Command[] commands) {
        for (Command command : commands) {
            this.registerCommand(command);
        }
    }

    public ArrayList<Command> getCommands() {
        return this.commands;
    }

    /**
     * Initialise all the commands a player can execute.
     */
    private void initialiseCommands() {
        final Command[] DEFAULT_COMMANDS = new Command[] {
            new CommandHelp(this),
            new CommandGo(),
            new CommandBack(),
            new CommandPet(),
            new CommandUse(),
            new CommandTake(),
            new CommandQuit()
        };

        this.registerCommands(DEFAULT_COMMANDS);
    }

    /**
     * Interpret a given command and execute it within the scope of a given world.
     * @param world Current World object
     * @param cmd Arbitrary input to match against
     * @return Boolean indicating whether the game loop should exit.
     */
    public boolean runCommand(World world, String cmd) {
        for (Command command : this.commands) {
            for (Pattern pattern : command.getPatterns()) {
                Matcher matcher = pattern.matcher(cmd);
                if (matcher.find()) {
                    return command.run(world, matcher);
                }
            }
        }

        System.out.println("Not sure what you're trying to do.");
        return false;
    }
}
