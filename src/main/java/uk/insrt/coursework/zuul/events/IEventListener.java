package uk.insrt.coursework.zuul.events;

public interface IEventListener<E extends Event> {
    public void onEvent(E event);
}
