package events;

import entities.Player;

public class Spikes_Event extends Event {
    public Spikes_Event() {
        super();
        choiceTime = false;
        dialogue = new String[]
                {"Say you were at the edge of a cliff,\nstuck between [enemy name] and a \ncliff",
                "Fighting the [enemy name] might \nlead to an untimely demise, however \nthen same can be said about the \ncliff",
                "What will you do?"};
        choices = new  String[] {"Fight the [enemy name]", "Jump off the cliff"};
        nextDialogue = 0;
    }

    @Override
    protected void processEvent(int choice) {
        choiceTime = false;
        nextDialogue = 0;

        if (choice == OPTION_1) {
            dialogue = new String[] {"The [enemy name] ended you."};
        } else if (choice == OPTION_2) {
            dialogue = new String[] {"You were saved by a mysterious traveler on your fall down"};
        } else {
            throw new RuntimeException("shouldn't be happening");
        }
    }

    @Override
    public void results(Player player) {
        player.loseHP(2);
        System.out.println("WORKING");
    }
}
