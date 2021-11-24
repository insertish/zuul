package uk.insrt.coursework.zuul.dialogue;

import java.util.HashMap;

import uk.insrt.coursework.zuul.io.IOSystem;

public class Dialogue<T extends Enum<T>> {
    private HashMap<T, DialogueNode<T>> parts;
    private T currentNode;

    public Dialogue(T start) {
        this.parts = new HashMap<>();
        this.currentNode = start;
    }

    public void addPart(T part, DialogueNode<T> node) {
        this.parts.put(part, node);
    }

    public void run(IOSystem io) {
        var part = this.parts.get(this.currentNode);
        io.println("\n" + part.getDescription());
        DialogueOption<T> option = part.pickOption(io);
        this.currentNode = option.getTarget();

        if (option.shouldExit()) {
            return;
        }

        this.run(io);
    }
}
