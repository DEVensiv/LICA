package statemachine.states;

import statemachine.Context;
import statemachine.commands.Command;
import statemachine.events.Idle;
import statemachine.events.InfoLineEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Connected implements State{
    @Override
    public State execute(Context ctx) {
        Command cmd;
        try {
            ctx.outEventQueue.add(new InfoLineEvent("Warte auf Anweisungen."));
            ctx.outEventQueue.add(new Idle(true,true,true));
            cmd = ctx.inCommandQueue.take();
            ctx.outEventQueue.add(new Idle(false,false,false));
        } catch (InterruptedException e) {
            Logger.getLogger("reactor").log(Level.WARNING,"Process Interrupted", e);
            return this;
        }

        switch (cmd.getClass().getSimpleName()) {
            case "Start":
                return new AutoCalibrate();
            case "Stop":
                return null;
            case "TestSensor":
                return new SensorTest();
            default:
                return this;
        }
    }
}
