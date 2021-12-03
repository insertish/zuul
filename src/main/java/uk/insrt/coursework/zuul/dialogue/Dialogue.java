package uk.insrt.coursework.zuul.dialogue;

import java.util.HashMap;

import uk.insrt.coursework.zuul.io.IOSystem;

/**
 * Simple dialogue engine which navigates between {@link DialogueNode}(s).
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class Dialogue<T> {
    private HashMap<T, DialogueNode<T>> parts;
    private T currentNode;

    /**
     * Contruct a new Dialogue engine.
     */
    public Dialogue() {
        this.parts = new HashMap<>();
    }

    /**
     * Construct a new Dialogue engine and initialise us at a starting node.
     * @param start Starting node
     */
    public Dialogue(T start) {
        this();
        this.currentNode = start;
    }

    /**
     * Get the current node
     */
    public T getCurrentNode() {
        return this.currentNode;
    }

    /**
     * Set the current node
     * @param node New node
     */
    public void getCurrentNode(T node) {
        this.currentNode = node;
    }

    /**
     * Change the current node to a different one if it exists
     * @param node New node
     */
    public void setNodeIfPresent(T node) {
        if (this.parts.containsKey(node)) {
            this.currentNode = node;
        }
    }

    /**
     * Add a new part to the dialogue
     * @param part What this node is identified by
     * @param node The new node
     */
    public void addPart(T part, DialogueNode<T> node) {
        this.parts.put(part, node);
    }

    /**
     * Get an existing part from the dialogue
     * @param part What the node is identified by
     * @return The node if it exists, otherwise null
     */
    public DialogueNode<T> getPart(T part) {
        return this.parts.get(part);
    }

    /**
     * Run the Dialogue engine until one of the options exits us out
     * @param io Provided IO system
     */
    public void run(IOSystem io) {
        var part = this.parts.get(this.currentNode);
        io.println("\n" + part.getDescription());
        DialogueOption<T> option = part.pickOption(io);

        T target = option.handle(io);
        if (target == null) {
            T newTarget = option.getTarget();
            if (newTarget != null) {
                this.currentNode = newTarget;
            }

            
            return;
        }

        this.currentNode = target;
        this.run(io);
    }
}
