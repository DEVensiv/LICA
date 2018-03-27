package statemachine.events;

import java.io.IOException;

public class ConnectionInterrupted implements Event {
    IOException exception;

    public ConnectionInterrupted(IOException exception) {
        this.exception = exception;
    }
}
