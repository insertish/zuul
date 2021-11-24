package uk.insrt.coursework.zuul.dialogue;

import uk.insrt.coursework.zuul.io.IOSystem;

public class DialogueOption<T extends Enum<T>> {
    private IDialogueHandler<T> handler;

    private String description;
    private boolean shouldExit;
    private T target;

    public DialogueOption(String description, T target) {
        this.target = target;
        this.description = description;
    }

    public DialogueOption(String description, IDialogueHandler<T> handler) {
        this.handler = handler;
        this.description = description;
    }
    
    public DialogueOption<T> mustExit() {
        this.shouldExit = true;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public T getTarget() {
        return this.target;
    }

    public T handle(IOSystem io) {
        if (this.handler != null) {
            return this.handler.onAction(io);
        } else if (!this.shouldExit) {
            return this.target;
        }

        return null;
    }
}
