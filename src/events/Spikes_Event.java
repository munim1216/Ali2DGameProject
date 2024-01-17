package events;

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
}
