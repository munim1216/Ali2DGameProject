package events;

public class Spikes_Event extends Event {
    public Spikes_Event() {
        super();
        choiceTime = false;
        dialogue = new String[] {"Say you were at the edge of a cliff, stuck between [enemy name] and a cliff",
                "Fighting the [enemy name] might lead to an untimely demise, however the same can be said about the cliff", "" +
                "What will you do?"};
        choices = new  String[] {"Fight the [enemy name]", "Jump off the cliff"};
        nextDialogue = 0;
    }
}
