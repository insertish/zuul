package uk.insrt.coursework.zuul.content.campaign;

public class StoryFlags {
    public enum Stage {
        Exposition,
        Recon,
        Stealth,
        Twist,
        Conclusion,
    }

    private Stage stage;

    public StoryFlags() {
        this.stage = Stage.Exposition;
    }

    public Stage getStage() {
        return this.stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
