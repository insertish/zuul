package uk.insrt.coursework.zuul.dialogue;

import uk.insrt.coursework.zuul.io.IOSystem;

public interface IDialogueHandler<T extends Enum<T>> {
    public T onAction(IOSystem io);
}
