package events;

public class Spikes_Event extends Event {
    public Spikes_Event() {
        super();
        happening = false;
    }
    protected void startEvent() {
        gp.startEvent();
    }

    // draw window, write text, possibly multiple boxes, maybe a selection area at the end?

}
