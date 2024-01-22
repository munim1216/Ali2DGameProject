package events;
import main.GamePanel;
import main.UI;

import java.awt.*;

public class EventHandler {
    private Event[] events;
    private Event currentEvent;
    private boolean eventHappening;
    private Rectangle solidArea;
    public final int rectangleDefaultX;
    public final int rectangleDefaultY;
    private int worldX;
    private int worldY;
    public EventHandler() {
        eventHappening = false;
        currentEvent = null;

        worldX = 0;
        worldY = 0;

        rectangleDefaultX = 48;
        rectangleDefaultY = 48;

        solidArea = new Rectangle(0,0,rectangleDefaultX, rectangleDefaultY);

        events = new Event[] {new Spikes_Event()};
    }

    // to ensure the events arent always happening, just a chance of happening
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

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    // so when u get the same event again it isnt already done
    public void resetEvent() {
        currentEvent.reset();
        currentEvent = null;
        eventHappening = false;
    }
    public void processEvent(int choice) {
        currentEvent.processEvent(choice);
    }

    private Event pickEvent() {
        //int eventNum = (int) ((Math.random() * 2) + 1);
        // currently always returns events[0] bc only one event has been implemented
        return events[0];
    }
}
