package uk.insrt.coursework.zuul.commands;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.commands.core.CommandBack;
import uk.insrt.coursework.zuul.commands.core.CommandBag;
import uk.insrt.coursework.zuul.commands.core.CommandDrop;
import uk.insrt.coursework.zuul.commands.core.CommandGo;
import uk.insrt.coursework.zuul.commands.core.CommandHelp;
import uk.insrt.coursework.zuul.commands.core.CommandPet;
import uk.insrt.coursework.zuul.commands.core.CommandQuit;
import uk.insrt.coursework.zuul.commands.core.CommandTake;
import uk.insrt.coursework.zuul.commands.core.CommandTalk;
import uk.insrt.coursework.zuul.commands.core.CommandUse;
import uk.insrt.coursework.zuul.commands.core.CommandWhereAmI;
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
        final Command[] DEFAULT_COMMANDS = {
            new CommandBack(),
            new CommandBag(),
            new CommandDrop(),
            new CommandGo(),
            new CommandHelp(this),
            new CommandPet(),
            new CommandQuit(),
            new CommandTake(),
            new CommandTalk(),
            new CommandUse(),
            new CommandWhereAmI(),
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
                    Arguments arguments = new Arguments(matcher);
                    return command.run(world, arguments);
                }
            }
        }

        world.getIO().println("Not sure what you're trying to do.");
        return false;
    }
}
