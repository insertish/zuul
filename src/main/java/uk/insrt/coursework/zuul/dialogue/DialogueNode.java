package uk.insrt.coursework.zuul.dialogue;

import java.util.ArrayList;
import java.util.List;

import uk.insrt.coursework.zuul.io.IOSystem;

/**
 * A node in a {@link Dialogue} system.
 */
public class DialogueNode<T> {
    private String description;
    private ArrayList<DialogueOption<T>> options;

    /**
     * Construct a new node.
     * @param description Description of this node
     */
    public DialogueNode(String description) {
        this.description = description;
        this.options = new ArrayList<>();
    }

    /**
     * Get this node's description
     * @return Description string
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Add a new option which branches off this node.
     * @param option Dialogue Option
     * @return This Dialogue Node so other method calls can be chained
     */
    public DialogueNode<T> addOption(DialogueOption<T> option) {
        this.options.add(option);
        return this;
    }

    /**
     * Create a new option which branches off this node.
     * @param description Description of this option
     * @param stage The next stage of the dialogue this should jump to
     * @param mustExit Whether we must exit from the dialogue after selecting this option
     * @return This Dialogue Node so other method calls can be chained
     */
    public DialogueNode<T> addOption(String description, T stage, boolean mustExit) {
        var option = new DialogueOption<T>(description, stage);
        if (mustExit) option.mustExit();
        this.options.add(option);
        return this;
    }

    /**
     * Create a new option which branches off this node.
     * @param description Description of this option
     * @param stage The next stage of the dialogue this should jump to
     * @return This Dialogue Node so other method calls can be chained
     */
    public DialogueNode<T> addOption(String description, T stage) {
        return this.addOption(description, stage, false);
    }

    /**
     * Get the options available to this node.
     * @return List of options
     */
    protected List<DialogueOption<T>> getOptions() {
        return this.options;
    }

    /**
     * Ask the player to pick one of the valid options branching off this node.
     * @param io Provided IO system
     * @return The selected option
     */
    public DialogueOption<T> pickOption(IOSystem io) {
        List<DialogueOption<T>> options = this.getOptions();
        for (int i=0;i<options.size();i++) {
            io.println((i + 1) + ". " + options.get(i).getDescription());
        }

        while (true) {
            io.print("Choice: ");
            String value = io.readLine();
            try {
                int v = Integer.parseInt(value);
                if (v < 1 || v > options.size()) {
                    io.println("Provide a valid option!");
                    continue;
                }

                return options.get(v - 1);
            } catch (Exception e) {
                io.println("Provide a valid number!");
            }
        }
    }
}
