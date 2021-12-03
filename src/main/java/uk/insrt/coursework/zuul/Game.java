package uk.insrt.coursework.zuul;

import java.io.IOException;

import javax.swing.JOptionPane;

import uk.insrt.coursework.zuul.commands.CommandManager;
import uk.insrt.coursework.zuul.content.campaign.CampaignWorld;
import uk.insrt.coursework.zuul.content.campaign.commands.CommandMap;
import uk.insrt.coursework.zuul.content.campaign.commands.CommandWin;
import uk.insrt.coursework.zuul.events.world.EventProcessCommand;
import uk.insrt.coursework.zuul.events.world.EventTick;
import uk.insrt.coursework.zuul.io.IOSystem;
import uk.insrt.coursework.zuul.io.LocalisedIO;
import uk.insrt.coursework.zuul.io.SanitiseIO;
import uk.insrt.coursework.zuul.io.StandardIO;
import uk.insrt.coursework.zuul.sound.EventMusic;
import uk.insrt.coursework.zuul.sound.MusicType;
import uk.insrt.coursework.zuul.sound.SoundManager;
import uk.insrt.coursework.zuul.ui.EventDraw;
import uk.insrt.coursework.zuul.ui.TerminalEmulator;
import uk.insrt.coursework.zuul.util.BlueJ;
import uk.insrt.coursework.zuul.util.Localisation;
import uk.insrt.coursework.zuul.world.World;

/**
 * Class for managing the game loop and initialising the world.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.1-SNAPSHOT
 */
public class Game {
    public static final String GAME_NAME = "World of These";

    private World world;
    private IOSystem io;
    private CommandManager commands;
    private SoundManager soundManager;

    /**
     * Entrypoint to our application.
     * @param args Arguments provided to the application
     */
    public static void main(String[] args) {
        new Game().play();
    }

    /**
     * Initialise and start the game.
     */
    public void play() {
        this.init();
        this.start();
    }

    /**
     * Initialise all required resources for the game to run.
     */
    private void init() {
        // Load audio resources.
        this.soundManager = new SoundManager();

        // Determine how the game should run.
        boolean inBlueJ = BlueJ.isRunningInBlueJ();
        int selection = JOptionPane.showConfirmDialog(null, "Play full experience?\nUses custom terminal emulator.\n(recommended option)", GAME_NAME, JOptionPane.YES_NO_OPTION);
        if (selection != 1) {
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
            this.io = new StandardIO();

            if (inBlueJ) {
                this.io = new SanitiseIO(this.io);
            }
        }

        // Notify player that resources are loading.
        this.io.println("Loading resources...\nThis may take a second.");

        // Setup the command manager.
        this.commands = new CommandManager();
        this.commands.registerCommand(new CommandWin());

        // Register the Map command if we're in term emu mode.
        // We draw images here so it's not available generally.
        if (this.io instanceof TerminalEmulator) {
            CommandMap map = new CommandMap();
            this.commands.registerCommand(map);
            ((TerminalEmulator) this.io).getEventSystem().addListener(EventDraw.class, map);
        }

        // Load sounds
        try {
            this.soundManager.init();
        } catch (Exception e) {
            e.printStackTrace();
            // If we can't load the sound system,
            // just play without sound.
        }

        // Load all the data we need and initialise world.
        Localisation locale = new Localisation();
        this.io = new LocalisedIO(this.io, locale);

        // Prompt for language
        this.io.clear();
        this.io.print("Welcome! \u1F604\nBefore we start...\n\n"
            + "\u1F508 Note: \u001B[35mthis game uses sound.\u001B[0m \n\n"
            + "What language would you like to use?\n"
            + "1. :uk:Traditional English (recommended)\n"
            + "2. :us:Simplified English\n"
            + "3. :de:German\n"
            + "4. :cz:Czech\n"
            + "Selection: ");

        String input = this.io.readLine();
        this.io.clear();

        String selectedLanguage;
        if (input.equals("3")) {
            selectedLanguage = "de_DE";
        } else if (input.equals("4")) {
            selectedLanguage = "cs_CZ";
        } else {
            selectedLanguage = "en_GB";
        }

        try {
            locale.loadLocale(selectedLanguage);
        } catch (IOException e) {
            System.err.println("Failed to load translations!");
            e.printStackTrace();
        }

        // Construct the world.
        this.world = new CampaignWorld(this.io);

        // Register sound events.
        this.soundManager.register(this.world.getEventSystem());
        this.world.getEventSystem().emit(new EventMusic(MusicType.BgmExplore, true));
    }

    /**
     * Start the game loop.
     */
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

        this.io.println("Goodbye.");

        try {
            Thread.sleep(1000);
            this.io.dispose();
        } catch (Exception e) {}

        this.soundManager.dispose();
    }
}
