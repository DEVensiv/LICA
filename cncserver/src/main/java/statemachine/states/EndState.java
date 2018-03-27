package statemachine.states;

import statemachine.Context;
import statemachine.events.ConnectionInterrupted;
import statemachine.events.InfoLineEvent;

import java.io.IOException;

public class EndState implements State {
    @Override
    public State execute(Context ctx) {
        try {
            ctx.arduinoR1.sendCommand("XPS:15,0,0,0;");
            ctx.outEventQueue.add(new InfoLineEvent("Abgaberoutine gestartet."));
            System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
            ctx.arduinoR1.sendCommand("ROT:930,0,0,0;");
            System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
            ctx.arduinoR1.sendCommand("XPS:32,0,0,0;");
            ctx.outEventQueue.add(new InfoLineEvent("Fahre auf Abgabe Position."));
            System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
            ctx.arduinoR1.sendCommand("MOT:5,0,0,0;");
            sleep(2000);
            ctx.arduinoR1.sendCommand("ZMX;");
            System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
            ctx.outEventQueue.add(new InfoLineEvent("Werkstück abgegeben."));
            ctx.arduinoR1.sendCommand("XPS:15,0,0,0;");
            System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
            ctx.arduinoR1.sendCommand("ROT:135,0,0,0;");
            System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
        } catch (IOException e) {
            ctx.outEventQueue.add(new ConnectionInterrupted(e));
            return new StartState();
        }
        return new Connected();
    }
    void sleep(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    void sleep(int i){

        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
