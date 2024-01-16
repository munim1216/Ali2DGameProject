package events;

public class Event {
    protected String[] dialogue;
    protected int nextDialogue;
    protected boolean drawable;

    protected Event(){}

    protected String getDialogue() {
        String nextText = dialogue[nextDialogue];
        nextDialogue++;
        return nextText;
    }
}
