package config;

import statemachine.Context;
import statemachine.events.InfoLineEvent;

import java.io.IOException;

public class Drill implements Task {
    int x;
    int y;
    int z;
    int speed;

    public Drill(int x, int y, int z, int speed) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.speed = speed;
        System.out.println("New Drill: " + x + "," + y + "," + z + "," + speed);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getSpeed(){
        return speed;
    }

    @Override
    public void executeTask(Context ctx) throws IOException {
//17E = 1mm
        ctx.outEventQueue.add(new InfoLineEvent("Bohrung wird vorbereitet."));
        ctx.arduinoB1.sendCommand("XPS:170,0,0,0;");
        System.out.println("B1:" + ctx.arduinoB1.receiveResponse());
        ctx.arduinoR1.sendCommand("XPS:2,0,0,0;");
        System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
        ctx.arduinoR1.sendCommand("MOT:4,0,255,0;");
        sleep();
        ctx.arduinoR1.sendCommand("ROT:530,0,0,0;");
        System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
        ctx.arduinoB1.sendCommand("XNL;");
        System.out.println("B1:" + ctx.arduinoB1.receiveResponse());
        ctx.arduinoR1.sendCommand("ZMX;");
        System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
        ctx.arduinoR1.sendCommand("MOT:5,0,0,0;");
        sleep(2000);
        ctx.arduinoB1.sendCommand("MOT:4,1,255,0;");
        sleep();
        ctx.arduinoR1.sendCommand("ZNL;");
        System.out.println("R1:" + ctx.arduinoR1.receiveResponse());

        Drill drill = this;
        while (drill != null) {

            ctx.outEventQueue.add(new InfoLineEvent("Starte Bohrung."));
            ctx.arduinoB1.sendCommand("XPS:" + drill.getX() + ",0,0,0;");
            System.out.println("B1:" + ctx.arduinoB1.receiveResponse());
            ctx.arduinoB1.sendCommand("YPS:" + drill.getY() + ",0,0,0;");
            System.out.println("B1:" + ctx.arduinoB1.receiveResponse());
            ctx.arduinoB1.sendCommand("MOT:5,0,100,0;");
            sleep();
            ctx.outEventQueue.add(new InfoLineEvent("Bohrung wird durchgeführt."));
            ctx.arduinoB1.sendCommand("ZPS:" + (drill.getZ()+100) + "," + drill.getSpeed() + ",0,0;");
            System.out.println("B1:" + ctx.arduinoB1.receiveResponse());

            ctx.arduinoB1.sendCommand("ZNL;");
            System.out.println(ctx.arduinoB1.receiveResponse());
            ctx.arduinoB1.sendCommand("MOT:5,0,0,0;");
            ctx.outEventQueue.add(new InfoLineEvent("Bohrung fertiggestellt."));
            if(!ctx.taskStack.isEmpty() && ctx.taskStack.get(0) instanceof Drill) {
           drill = (Drill) ctx.taskStack.remove(0);
                ctx.outEventQueue.add(new InfoLineEvent("Nächste Bohrung wird Vorbereitet."));
           }else {
                drill = null;
            }
        }
        ctx.outEventQueue.add(new InfoLineEvent("Alle Bohrungen wurden durchgeführt."));
        ctx.arduinoB1.sendCommand("ZNL;");
        System.out.println(ctx.arduinoB1.receiveResponse());
        ctx.arduinoB1.sendCommand("MOT:5,0,0,0;");
        sleep();
        ctx.arduinoB1.sendCommand("XNL;");
        sleep();
        ctx.arduinoB1.sendCommand("YNL;");
        for(int i = 0; i <=1; i++) {
            System.out.println("B1:" + ctx.arduinoB1.receiveResponse());
        }

        ctx.arduinoR1.sendCommand("ZMX;");
        System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
        ctx.arduinoR1.sendCommand("MOT:5,0,255,0;");
        sleep();
        ctx.arduinoB1.sendCommand("MOT:4,0,255,0;");
        sleep();
        ctx.arduinoR1.sendCommand("ZNL;");
        System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
        ctx.arduinoB1.sendCommand("XPS:170,0,0,0;");
        System.out.println("B1:" + ctx.arduinoB1.receiveResponse());
        ctx.arduinoR1.sendCommand("ROT:135,0,0,0;");
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
