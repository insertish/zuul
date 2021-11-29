package uk.insrt.coursework.zuul.content.campaign.events;

import uk.insrt.coursework.zuul.content.campaign.StoryFlags.Stage;
import uk.insrt.coursework.zuul.events.Event;

/**
 * Event fired when the story stage (chapter) changes.
 */
public class EventGameStageChanged extends Event {
    private Stage stage;

    /**
     * Construct a new GameStageChanged event.
     * @param stage New stage
     */
    public EventGameStageChanged(Stage stage) {
        this.stage = stage;
    }

    /**
     * Get the new game stage.
     * @return Stage
     */
    public Stage getStage() {
        return this.stage;
    }
}
