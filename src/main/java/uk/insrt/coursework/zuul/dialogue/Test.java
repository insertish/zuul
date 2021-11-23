package uk.insrt.coursework.zuul.dialogue;

import java.util.ArrayList;
import java.util.HashMap;

import uk.insrt.coursework.zuul.io.IOSystem;
import uk.insrt.coursework.zuul.ui.TerminalEmulator;

public class Test {
    public enum Part {
        INITIAL_STATE,
        AMOGUS
    }

    public static class Option<T> {
        private String description;
        private boolean exit;
        private T target;

        public Option(T target, String description) {
            this.target = target;
            this.description = description;
        }
        
        public Option<T> mustExit() {
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

    public static class DialogueNode<T> {
        private String description;
        private ArrayList<Option<T>> options;

        public DialogueNode(String description) {
            this.description = description;
            this.options = new ArrayList<>();
        }

        public String getDescription() {
            return this.description;
        }

        public DialogueNode<T> addOption(Option<T> option) {
            this.options.add(option);
            return this;
        }

        public Option<T> pickOption(IOSystem io) {
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

    public static class GenericDialogue<T extends Enum<T>> {
        private HashMap<T, DialogueNode<T>> parts;
        private T currentNode;

        public GenericDialogue(T start) {
            this.parts = new HashMap<>();
            this.currentNode = start;
        }

        public void addPart(T part, DialogueNode<T> node) {
            this.parts.put(part, node);
        }

        public void run(IOSystem io) {
            var part = this.parts.get(this.currentNode);
            io.println("\n" + part.getDescription());
            Option<T> option = part.pickOption(io);
            this.currentNode = option.getTarget();

            if (option.shouldExit()) {
                return;
            }

            this.run(io);
        }
    }

    public static void main(String[] args) {
        var dialogue = new GenericDialogue<Part>(Part.INITIAL_STATE);

        dialogue.addPart(
            Part.INITIAL_STATE,
            new DialogueNode<Part>("this is the initial state")
                .addOption(new Option<Part>(Part.AMOGUS, "no choice but amogus"))
        );
        
        dialogue.addPart(
            Part.AMOGUS,
            new DialogueNode<Part>("this is the final state")
                .addOption(new Option<Part>(Part.INITIAL_STATE, "gtfo").mustExit())
                .addOption(new Option<Part>(Part.INITIAL_STATE, "no gtfo"))
        );

        var io = new TerminalEmulator();
        dialogue.run(io);
    }
}
