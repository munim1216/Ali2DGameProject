package events;
import main.GamePanel;
import main.UI;

public class EventHandler {
    private Event[] events;
    private Event currentEvent;
    private boolean eventHappening;
    public EventHandler() {
        eventHappening = false;
        currentEvent = null;
        events = new Event[] {new Spikes_Event()};
    }

    public void tryForEvent() {
        if (!eventHappening && ((Math.random() * 11) + 1)  <  3) {
            currentEvent = pickEvent();
            currentEvent.startEvent();
            eventHappening = true;
        }
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    private Event pickEvent() {
        //int eventNum = (int) ((Math.random() * 2) + 1);
        return events[0];
    }
}
