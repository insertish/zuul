package uk.insrt.coursework.zuul.dialogue;

import java.util.ArrayList;

import uk.insrt.coursework.zuul.io.IOSystem;

public class DialogueNode<T> {
    private String description;
    private ArrayList<DialogueOption<T>> options;

    public DialogueNode(String description) {
        this.description = description;
        this.options = new ArrayList<>();
    }

    public String getDescription() {
        return this.description;
    }

    public DialogueNode<T> addOption(DialogueOption<T> option) {
        this.options.add(option);
        return this;
    }

    public DialogueNode<T> addOption(String description, T stage, boolean mustExit) {
        var option = new DialogueOption<T>(description, stage);
        if (mustExit) option.mustExit();
        this.options.add(option);
        return this;
    }

    public DialogueNode<T> addOption(String description, T stage) {
        return this.addOption(description, stage, false);
    }

    public DialogueOption<T> pickOption(IOSystem io) {
        for (int i=0;i<this.options.size();i++) {
            io.println((i + 1) + ". " + this.options.get(i).getDescription());
        }

        while (true) {
            io.print("Choice: ");
            String value = io.readLine();
            try {
                int v = Integer.parseInt(value);
                if (v < 1 || v > this.options.size()) {
                    io.println("Provide a valid option!");
                    continue;
                }

                return this.options.get(v - 1);
            } catch (Exception e) {
                io.println("Provide a valid number!");
            }
        }
    }
}
