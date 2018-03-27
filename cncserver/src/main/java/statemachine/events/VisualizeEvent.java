package statemachine.events;

import config.WorkpieceID;

public class VisualizeEvent implements Event{
    String fromArdu;

    public VisualizeEvent(String code) {
        fromArdu = code;
    }

    public String getFromArdu() {
        return fromArdu;
    }
}
