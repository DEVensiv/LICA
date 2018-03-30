package statemachine.events;

public class VisualizeEvent implements Event{
    String fromArdu;

    public VisualizeEvent(String code) {
        fromArdu = code;
    }

    public String getFromArdu() {
        return fromArdu;
    }
}
