package events;
import main.GamePanel;

public class Event {
    protected String[] dialogue;
    protected int nextDialogue;
    protected boolean drawable;
    protected static GamePanel gp;

    protected Event(){
        EventHandler.addEvent(this);
    }

    public static void addGP(GamePanel gp) {
        Event.gp = gp;
    }
    protected String getDialogue() {
        String nextText = dialogue[nextDialogue];
        nextDialogue++;
        return nextText;
    }

    protected void startEvent() {

    }

    protected void endEvent() {
        gp.endEvent();
    }
}
