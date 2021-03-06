package uk.insrt.coursework.zuul.content.campaign;

import java.util.HashSet;

import uk.insrt.coursework.zuul.content.campaign.events.EventGameStageChanged;
import uk.insrt.coursework.zuul.events.EventSystem;
import uk.insrt.coursework.zuul.events.world.EventTick;

/**
 * Class which controls story progression within the Campaign World.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class StoryFlags {
    /**
     * The current story chapter.
     */
    public enum Stage {
        Exposition, // Ch 1.
        Recon, // Ch 2.
        Stealth, // Ch 3.
        End, // Current Ending

        Twist, // Ch 4. Skipped
        Conclusion, // Ch 5. Skipped
    }

    /**
     * Side-quests available in the game.
     */
    public enum Quest {
        Cat,
        WormHole
    }
    
    private EventSystem eventSystem;
    private HashSet<Quest> quests;

    private long startTime;
    private int balance;
    private Stage stage;
    private int ticks;

    /**
     * Construct a new instance of StoryFlags
     * @param eventSystem World event system
     */
    public StoryFlags(EventSystem eventSystem) {
        this.eventSystem = eventSystem;
        this.stage = Stage.Exposition;
        this.quests = new HashSet<>();

        this.startTime = System.currentTimeMillis();
        this.balance = 100_000;
        this.ticks = 0;

        this.eventSystem.addListener(EventTick.class, e -> this.ticks++);
    }

    /**
     * Get the current stage (chapter) of the story.
     * @return Current stage
     */
    public Stage getStage() {
        return this.stage;
    }

    /**
     * Set the current stage (chapter) of the story.
     * @param stage New stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        this.eventSystem.emit(new EventGameStageChanged(stage));
    }

    /**
     * Get the player's balance
     * @return Player's balance
     */
    public int getBalance() {
        return this.balance;
    }

    /**
     * Set player's new balance.
     * @param balance New balance
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Deduct money from the player's balance.
     * @param value Amount to deduct
     * @return Whether we could deduct the balance without going below zero
     */
    public boolean deductFromBalance(int value) {
        if (value > this.balance) {
            return false;
        }

        this.balance -= value;
        return true;
    }

    /**
     * Get ticks since start of the World.
     * @return Number of ticks since start
     */
    public int getTicks() {
        return this.ticks;
    }

    /**
     * Mark a side-quest as complete
     */
    public void completeSideQuest(Quest quest) {
        this.quests.add(quest);
    }

    /**
     * Get completed side-quests.
     * @return Number of completed side-quests
     */
    public int getCompletedQuests() {
        return this.quests.size();
    }

    /**
     * Get total number of side-quests.
     * @return Total number of side-quests
     */
    public int getTotalQuests() {
        return Quest.values().length;
    }

    /**
     * Get time elapsed since the start of the game.
     * @return Time elapsed
     */
    public long timeElapsed() {
        return System.currentTimeMillis() - this.startTime;
    }

    /**
     * Take the time elapsed and pretty print it.
     * @return Pretty printed time elapsed.
     */
    public String prettyPrintTimeElapsed() {
        long time = this.timeElapsed() / 1000;
        return (time / 60) + " <commands.win.minutes> "
            + (time % 60) + " <commands.win.seconds>";
    }
}
