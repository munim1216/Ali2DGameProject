package events;

import java.awt.*;

public class EventHandler {
    protected static Event[] events = new Event[10];
    private static int nextEvent = 0;
    private static int counter;
    private static final int CHECK_AGAIN = 60;

    private EventHandler (){}

    protected static void addEvent(Event event) {
        events[nextEvent] = event;
        nextEvent++;
    }

    public static void update() {
        counter++;
        if (counter < CHECK_AGAIN) {
            return;
        }
        for (int i = 0; events[i] != null; i++) {
           events[i].update();
        }
    }

    public static void draw(Graphics2D g2D) {
        for (int i = 0; events[i] != null; i++) {
            if (events[i].isHappening()) {
                Event.gp.UI.drawEvent(g2D, events[i]);
            }
        }
    }
}
