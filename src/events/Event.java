package events;
import main.GamePanel;
import main.UI;

public class Event {
    protected String[] dialogue;
    protected int nextDialogue;
    protected boolean drawable;
    protected boolean happening;
    protected static GamePanel gp;

    public Event(){
        EventHandler.addEvent(this);
    }

    public static void addGPandUI(GamePanel gp) {
        Event.gp = gp;
    }
    public boolean isHappening() {
        return happening;
    }
    public String getDialogue() {
        return dialogue[nextDialogue];
    }
    public void incrementDialogue() {
        nextDialogue++;
    }
    protected void startEvent() {

    }

    protected void endEvent() {
        gp.endEvent();
    }

    protected void update() {
        if (randNum() < 10) {
            System.out.println("achieved");
        }
    }

    private int randNum() {
        return (int) (Math.random() * 10000);
    }
}
