package statemachine.states;

import statemachine.Context;
import statemachine.commands.Command;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StartState implements State {

    @Override
    public State execute(Context ctx) {
        Command cmd;
        try {
            cmd = ctx.inCommandQueue.take();
            if (ctx.arduinoB1 != null)
                ctx.arduinoB1.close();
            if (ctx.arduinoF1 != null)
                ctx.arduinoF1.close();
            if (ctx.arduinoR1 != null)
                ctx.arduinoR1.close();
        } catch (InterruptedException e) {
            Logger.getLogger("reactor").log(Level.WARNING,"Process Interrupted", e);
            return this;
        }

        switch (cmd.getClass().getSimpleName()) {
            case "Connect":
                return new ConnectionPhase();
            case "Stop":
                return null;
            case "TestSensor":
                return new ConnectionPhase(true);
            default:
                return this;
        }
    }
}
