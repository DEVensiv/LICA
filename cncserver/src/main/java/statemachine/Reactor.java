package statemachine;

import config.Config;
import statemachine.commands.Command;
import statemachine.events.Event;
import statemachine.states.State;

import java.util.concurrent.TimeUnit;

public class Reactor {
    public static Context context;
    private Thread runner;

    public Reactor(State initialState, Config config) {
        context = new Context(initialState, config);
        runner = new Thread(() -> run(context));
        runner.start();
    }

    public boolean isRunning() {
        return runner != null && runner.isAlive();
    }

    public void sendCommand(Command cmd) {
        context.inCommandQueue.add(cmd);
    }

    public void stop(){
        context.arduinoR1.close();
        context.arduinoB1.close();
        context.arduinoF1.close();
        System.out.println(context.arduinoR1.toString());
        System.exit(1337);
    }

    public Event fetchEvent() {
        try {
            return context.outEventQueue.poll(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setConfig(Config cfg){
        context.config = cfg;
    }

    private static void run(Context context) {
        while (context.state != null) {
            context.state = context.state.execute(context);
        }
    }
}
