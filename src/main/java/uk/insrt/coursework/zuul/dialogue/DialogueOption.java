package uk.insrt.coursework.zuul.dialogue;

import uk.insrt.coursework.zuul.io.IOSystem;

/**
 * An option which branches off a {@link DialogueNode} into another node.
 */
public class DialogueOption<T> {
    private IDialogueHandler<T> handler;

    private String description;
    private boolean shouldExit;
    private T target;

    /**
     * Construct a new simple DialogueOption with a description and destination.
     * @param description Description of this option
     * @param target Target node to jump to
     */
    public DialogueOption(String description, T target) {
        this.target = target;
        this.description = description;
    }

    /**
     * Construct a complex DialogueOption with a description and select handler.
     * @param description Description of this option
     * @param handler Method called when this option is selected
     */
    public DialogueOption(String description, IDialogueHandler<T> handler) {
        this.handler = handler;
        this.description = description;
    }
    
    /**
     * Tell the Dialogue system to exit if this option is selected.
     * @return This dialogue option so method calls can be chained
     */
    public DialogueOption<T> mustExit() {
        this.shouldExit = true;
        return this;
    }

    /**
     * Get the description of this option.
     * @return Description string
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Get the destination of this option.
     * @return Destination if it exists
     */
    public T getTarget() {
        return this.target;
    }

    /**
     * Handle the player selecting this dialogue option.
     * @param io Provided IO system
     * @return The new node or null if we should exit and stay put.
     */
    public T handle(IOSystem io) {
        if (this.handler != null) {
            return this.handler.onAction(io);
        } else if (!this.shouldExit) {
            return this.target;
        }

        return null;
    }
}
