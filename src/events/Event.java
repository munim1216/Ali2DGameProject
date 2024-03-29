package events;
import entities.Player;
import main.GamePanel;

public class Event {
    protected String[] dialogue;
    protected String[] choices;
    protected int nextDialogue;
    protected boolean choiceTime;
    protected static GamePanel gp;
    protected final int OPTION_1 = 0, OPTION_2 = 1;
    protected int choice;


    public Event(){}

    public static void setGP(GamePanel gp) {
        Event.gp = gp;
    }

    protected void startEvent() {
        gp.startEvent();
    }

    public String getDialogue() {
        return dialogue[nextDialogue];
    }

    public void incrementDialogue() {
        // to ensure an array out of bounds error doesnt happen
        nextDialogue++;
        if (nextDialogue >= dialogue.length) {
            nextDialogue = dialogue.length - 1;
            choiceTime = true;
        }
    }

    public boolean isChoiceTime() {
        return choiceTime;
    }

    public String[] getChoices() {
        return choices;
    }

    // empty methods created so subclasses can override it
    protected void processEvent(int choice) {

    }

    protected void reset() {

    }
    public void results(Player player) {

    }
}
