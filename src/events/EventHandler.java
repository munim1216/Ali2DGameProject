package events;

public class EventHandler {
    protected static Event[] events = new Event[10];
    private static int nextEvent = 0;

    private EventHandler (){}

    protected static void addEvent(Event event) {
        events[nextEvent] = event;
        nextEvent++;
    }

}
