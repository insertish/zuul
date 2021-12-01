package uk.insrt.coursework.zuul.content.campaign;

import uk.insrt.coursework.zuul.content.campaign.events.EventGameStageChanged;
import uk.insrt.coursework.zuul.events.EventSystem;

/**
 * Class which controls story progression within the Campaign World.
 */
public class StoryFlags {
    /**
     * The current story chapter.
     */
    public enum Stage {
        Exposition, // WIP
        Recon, // Unimplemented
        Stealth, // Unimplemented
        Twist, // Skipped
        Conclusion, // Skipped
    }
    
    private EventSystem eventSystem;
    private int balance;
    private Stage stage;

    /**
     * Construct a new instance of StoryFlags
     * @param eventSystem World event system
     */
    public StoryFlags(EventSystem eventSystem) {
        this.eventSystem = eventSystem;
        this.stage = Stage.Exposition;
        this.balance = 100_000;
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
}
