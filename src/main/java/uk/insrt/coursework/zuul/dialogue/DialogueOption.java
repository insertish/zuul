package uk.insrt.coursework.zuul.dialogue;

public class DialogueOption<T extends Enum<T>> {
    private String description;
    private boolean exit;
    private T target;

    public DialogueOption(T target, String description) {
        this.target = target;
        this.description = description;
    }
    
    public DialogueOption<T> mustExit() {
        this.exit = true;
        return this;
    }

    public boolean shouldExit() {
        return this.exit;
    }

    public String getDescription() {
        return this.description;
    }

    public T getTarget() {
        return this.target;
    }
}
