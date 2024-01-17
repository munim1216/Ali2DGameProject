package events;
import main.GamePanel;
import main.UI;

import java.awt.*;

public class EventHandler {
    private Event[] events;
    private Event currentEvent;
    private boolean eventHappening;
    private Rectangle solidArea;
    private final int rectangleDefaultX;
    private final int rectangleDefaultY;
    public EventHandler() {
        eventHappening = false;
        currentEvent = null;

        rectangleDefaultX = 48;
        rectangleDefaultY = 48;

        solidArea = new Rectangle(0,0,rectangleDefaultX, rectangleDefaultY);

        events = new Event[] {new Spikes_Event()};
    }

    public void tryForEvent() {
        if (!eventHappening && ((Math.random() * 11) + 1)  <  3) {
            currentEvent = pickEvent();
            currentEvent.startEvent();
            eventHappening = true;
        }
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void processEvent(int choice) {
        currentEvent.processEvent(choice);
    }

    private Event pickEvent() {
        //int eventNum = (int) ((Math.random() * 2) + 1);
        return events[0];
    }
}
