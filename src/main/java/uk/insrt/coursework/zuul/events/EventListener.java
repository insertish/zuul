package uk.insrt.coursework.zuul.events;

public interface EventListener<E extends Event> {
    public void onEvent(E event);
}
