package uk.insrt.coursework.zuul.content.campaign;

public class StoryFlags {
    public enum Stage {
        Exposition,
    }

    private Stage stage;

    public StoryFlags() {
        this.stage = Stage.Exposition;
    }

    public Stage getStage() {
        return this.stage;
    }
}
