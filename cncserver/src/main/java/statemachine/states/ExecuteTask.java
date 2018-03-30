package statemachine.states;

import config.Task;
import statemachine.Context;
import statemachine.events.ConnectionInterrupted;
import statemachine.events.InfoLineEvent;

import java.io.IOException;
import java.util.List;

public class ExecuteTask implements State{
    @Override
    public State execute(Context ctx) {
        List<Task> test = ctx.taskStack;
        Task task = ctx.taskStack.remove(0);

        try {
            ctx.arduinoR1.sendCommand("MOT:4,0,255,0;");
            ctx.arduinoR1.sendCommand("XNL;");
            System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
            task.executeTask(ctx);
            ctx.arduinoR1.sendCommand("MOT:4,0,0,0;");
        } catch (IOException e) {
            ctx.outEventQueue.add(new ConnectionInterrupted(e));
            return new StartState();
        }

        if (ctx.taskStack.isEmpty()) {
            ctx.outEventQueue.add(new InfoLineEvent("Alle Aufgaben Erledigt."));
            return new EndState();
        }else{
            return this;
        }
    }
}
