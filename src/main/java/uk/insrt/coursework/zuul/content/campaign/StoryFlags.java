package uk.insrt.coursework.zuul.content.campaign;

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

    private Stage stage;

    /**
     * Construct a new instance of StoryFlags
     */
    public StoryFlags() {
        this.stage = Stage.Exposition;
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
    }
}
