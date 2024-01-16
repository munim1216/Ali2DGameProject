package events;

public class Event {
    protected String[] dialogue;
    protected int nextDialogue;

    protected Event(){}

    protected String getDialogue() {
        String nextText = dialogue[nextDialogue];
        nextDialogue++;
        return nextText;
    }
}
