package statemachine;

import statemachine.events.Event;

import java.util.function.Consumer;

class Callback<T extends Event> {
    private final Class<T> eventType;
    private final Consumer<T> callbackFunction;

    Callback(Class<T> eventType, Consumer<T> callbackFunction) {
        this.eventType = eventType;
        this.callbackFunction = callbackFunction;
    }

    boolean eventTypeMatches(Event event) {
        return eventType.isInstance(event);
    }

    Consumer<T> getCallbackFunction() {
        return callbackFunction;
    }
}
