package uk.insrt.coursework.zuul.dialogue;

import uk.insrt.coursework.zuul.io.IOSystem;

/**
 * Interface implemented to provide an onAction method.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public interface IDialogueHandler<T> {
    /**
     * Handle the selection of a dialogue option.
     * @param io Provided IO system
     * @return Destination node, may be null
     */
    public T onAction(IOSystem io);
}
