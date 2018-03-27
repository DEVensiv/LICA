package statemachine.states;

import statemachine.Main;
import statemachine.Context;
import statemachine.commands.Connect;
import statemachine.commands.StopSensor;
import statemachine.events.B1Sensor;
import statemachine.events.F1Sensor;
import statemachine.events.R1Sensor;

import java.io.IOException;

public class SensorTest implements State {
    @Override
    public State execute(Context ctx) {
        try {
            if (ctx.arduinoB1 != null)
                ctx.arduinoB1.sendCommand("SNR:1,0,0,0;");
            if (ctx.arduinoF1 != null)
                ctx.arduinoF1.sendCommand("SNR:1,0,0,0;");
            if (ctx.arduinoR1 != null)
                ctx.arduinoR1.sendCommand("SNR:1,0,0,0;");
            Thread.sleep(1500);
        } catch (IOException e) {
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String B1, F1, R1;
        while (!(ctx.inCommandQueue.poll() instanceof StopSensor)) {

            if (ctx.arduinoB1 != null) {
                B1 = ctx.arduinoB1.receiveResponse();
                ctx.outEventQueue.add(new B1Sensor(B1));
            }
            if (ctx.arduinoF1 != null) {
                F1 = ctx.arduinoF1.receiveResponse();
                ctx.outEventQueue.add(new F1Sensor(F1));
            }
            if (ctx.arduinoR1 != null) {
                R1 = ctx.arduinoR1.receiveResponse();
                ctx.outEventQueue.add(new R1Sensor(R1));
            }
        }try{
        if (ctx.arduinoB1 != null)
            ctx.arduinoB1.sendCommand("SNR:0,0,0,0;");
        if (ctx.arduinoF1 != null)
            ctx.arduinoF1.sendCommand("SNR:0,0,0,0;");
        if (ctx.arduinoR1 != null)
            ctx.arduinoR1.sendCommand("SNR:0,0,0,0;");
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ctx.arduinoB1 != null) {
            while (ctx.arduinoB1.receiveResponse(1) != null) {
            }
        }
        if (ctx.arduinoF1 != null) {
            while (ctx.arduinoF1.receiveResponse(1) != null) {
            }
        }
        if (ctx.arduinoR1 != null) {
            while (ctx.arduinoR1.receiveResponse(1) != null) {
            }
        }
        if (ctx.arduinoR1 == null || ctx.arduinoB1 == null || ctx.arduinoF1 == null) {
            Main.sendCMD(new Connect());
            return new StartState();
        } else {
            return new Connected();
        }
    }
}
