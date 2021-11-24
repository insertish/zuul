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

    // inner type enum
    public static class Testing<T extends Enum<T>> {}
    public static class Extension extends Testing<Extension.InnerType> {
        public static enum InnerType {
            ABC,
            DEF
        }
    }

    public static void main(String[] args) {
        var dialogue = new Dialogue<Part>(Part.INITIAL_STATE);

        dialogue.addPart(
            Part.INITIAL_STATE,
            new DialogueNode<Part>("this is the initial state")
                .addOption(new DialogueOption<Part>(Part.AMOGUS, "no choice but amogus"))
        );
        
        dialogue.addPart(
            Part.AMOGUS,
            new DialogueNode<Part>("this is the final state")
                .addOption(new DialogueOption<Part>(Part.INITIAL_STATE, "gtfo").mustExit())
                .addOption(new DialogueOption<Part>(Part.INITIAL_STATE, "no gtfo"))
        );

        var io = new TerminalEmulator();
        dialogue.run(io);
    }
}
