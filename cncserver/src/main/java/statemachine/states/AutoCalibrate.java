package statemachine.states;

import statemachine.Context;
import statemachine.events.ConnectionInterrupted;
import statemachine.events.InfoLineEvent;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AutoCalibrate implements State{
    @Override
    public State execute(Context ctx) {
        ctx.outEventQueue.add(new InfoLineEvent("Starte Kalibrierung."));
        try {
            ctx.outEventQueue.add(new InfoLineEvent("Kalibiere Bohrer und Fräser"));
            ctx.arduinoB1.sendCommand("XNL;");
            ctx.arduinoF1.sendCommand("XNL;");
            System.out.println("F1:" + ctx.arduinoF1.receiveResponse());
            System.out.println("B1:" + ctx.arduinoB1.receiveResponse());
            ctx.arduinoB1.sendCommand("XPS:170,0,0,0;");
            ctx.arduinoF1.sendCommand("XPS:170,0,0,0;");
            System.out.println("F1:" + ctx.arduinoF1.receiveResponse());
            System.out.println("B1:" + ctx.arduinoB1.receiveResponse());
            ctx.arduinoR1.sendCommand("XNL;");
            System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
            ctx.arduinoR1.sendCommand("XPS:5,0,0,0;");
            System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
            ctx.arduinoR1.sendCommand("ROT:140,0,0,0;");
            System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
            ctx.arduinoR1.sendCommand("XNL;");
            System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
            ctx.arduinoR1.sendCommand("ZNL;");
            System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
            ctx.outEventQueue.add(new InfoLineEvent("Kalibiere Bohrer und Fräser"));
            ctx.arduinoB1.sendCommand("MOT:4,0,255,0;");
            sleep();
            ctx.arduinoB1.sendCommand("YNL;");
            sleep();
            ctx.arduinoB1.sendCommand("ZNL;");
            sleep();

            ctx.arduinoF1.sendCommand("MOT:4,0,255,0;");
            sleep();
            ctx.arduinoF1.sendCommand("YNL;");
            sleep();
            ctx.arduinoF1.sendCommand("ZNL;");
            sleep();

            for(int i = 0; i <= 1; i++) {
                System.out.println("B1:" + ctx.arduinoB1.receiveResponse());
                System.out.println("F1:" + ctx.arduinoF1.receiveResponse());
                Thread.sleep(100);
            }

        } catch (IOException e) {
            ctx.outEventQueue.add(new ConnectionInterrupted(e));
            return new StartState();
        } catch (InterruptedException e) {
            Logger.getLogger("reactor").log(Level.WARNING, "Thread sleep interrupted.", e);
        }
        ctx.outEventQueue.add(new InfoLineEvent("Kalibrierng abgeschlossen."));
        return new ScanPhase();
    }
    void sleep(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
