package uk.insrt.coursework.zuul.dialogue;

import java.util.HashMap;

import uk.insrt.coursework.zuul.io.IOSystem;

public class Dialogue<T> {
    private HashMap<T, DialogueNode<T>> parts;
    private T currentNode;

    public Dialogue() {
        this.parts = new HashMap<>();
    }

    public Dialogue(T start) {
        this();
        this.currentNode = start;
    }

    public void setNode(T node) {
        this.currentNode = node;
    }

    public void addPart(T part, DialogueNode<T> node) {
        this.parts.put(part, node);
    }

    public void run(IOSystem io) {
        var part = this.parts.get(this.currentNode);
        io.println("\n" + part.getDescription());
        DialogueOption<T> option = part.pickOption(io);

        T target = option.handle(io);
        if (target == null) {
            this.currentNode = option.getTarget();
            return;
        }

        this.currentNode = target;
        this.run(io);
    }
}
