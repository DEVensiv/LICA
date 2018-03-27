package statemachine;

import communication.ArduinoConnection;
import config.Task;
import statemachine.commands.Command;
import statemachine.events.Event;
import config.Config;
import statemachine.states.State;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Context {
    public ArduinoConnection arduinoB1;
    public ArduinoConnection arduinoF1;
    public ArduinoConnection arduinoR1;

    public LinkedBlockingQueue<Event> outEventQueue = new LinkedBlockingQueue<>();
    public LinkedBlockingQueue<Command> inCommandQueue = new LinkedBlockingQueue<>();

    public Config config;
    public List<Task> taskStack;

    State state;

    public Context(State initialState, Config config) {
        this.state = initialState;
        this.config = config;
    }
}
