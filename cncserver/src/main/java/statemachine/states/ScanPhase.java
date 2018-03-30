package statemachine.states;

import config.Config;
import config.WorkpieceID;
import statemachine.Context;
import statemachine.events.ConnectionInterrupted;
import statemachine.events.InfoLineEvent;
import statemachine.events.VisualizeEvent;

import java.io.File;
import java.io.IOException;

public class ScanPhase implements State{
    @Override
    public State execute(Context ctx) {
        ctx.outEventQueue.add(new InfoLineEvent("Lesen des Strichcode vorbereiten."));
        try {
            ctx.arduinoR1.sendCommand("ZMX;");
            System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
            ctx.arduinoR1.sendCommand("MOT:5,0,255,0;");
            ctx.arduinoR1.sendCommand("NMR;");
        } catch (IOException e) {
            ctx.outEventQueue.add(new ConnectionInterrupted(e));
            return new StartState();
        }
        String IDNMR = ctx.arduinoR1.receiveResponse();
        WorkpieceID piece = new WorkpieceID(IDNMR);
        try {
            ctx.config =  new Config(new File("data.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.taskStack = ctx.config.getTasks(piece);
        ctx.outEventQueue.add(new VisualizeEvent(IDNMR));
        ctx.outEventQueue.add(new InfoLineEvent("Strichcode gelesen: " + IDNMR));
        ctx.outEventQueue.add(new InfoLineEvent("Aktion " + piece.getValue() + " wird ausgeführt."));
        try {
            ctx.arduinoR1.sendCommand("ZNL;");
            System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
        } catch (IOException e) {
            ctx.outEventQueue.add(new ConnectionInterrupted(e));
            return new StartState();
        }

        //return new Connected();
        return new ExecuteTask();
    }
}
