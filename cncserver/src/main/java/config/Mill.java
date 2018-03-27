package config;

import statemachine.Context;
import statemachine.events.InfoLineEvent;

import java.io.IOException;
import java.util.List;

public class Mill implements Task {
    List<String> mill;

    public Mill(List<String> mills) {
        mill = mills;
        System.out.println("New Mill");
    }

    public List<String> getMill(){
        return mill;
    }

    @Override
    public void executeTask(Context ctx) throws IOException {
        sleep();
        ctx.outEventQueue.add(new InfoLineEvent("Fräsung wird vorbereitet."));
        ctx.arduinoF1.sendCommand("XPS:170,0,0,0;");
        System.out.println("F1:" + ctx.arduinoF1.receiveResponse());
        sleep();
        ctx.arduinoR1.sendCommand("XPS:31,0,0,0;");
        System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
        ctx.arduinoR1.sendCommand("MOT:4,0,255,0;");
        sleep();
        ctx.arduinoR1.sendCommand("ROT:530,0,0,0;");
        System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
        System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
        ctx.arduinoF1.sendCommand("XNL;");
        System.out.println("F1:" + ctx.arduinoF1.receiveResponse());
        ctx.arduinoR1.sendCommand("ZMX;");
        System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
        ctx.arduinoF1.sendCommand("MOT:4,1,255,0;");
        sleep();
        ctx.arduinoR1.sendCommand("MOT:5,0,0,0;");
        sleep(2000);
        ctx.arduinoR1.sendCommand("ZNL;");
        System.out.println("R1:" + ctx.arduinoR1.receiveResponse());

        Mill mills = this;
        while (mills != null) {
                List<String> mill1 = mills.mill;
            while (!mill1.isEmpty()) {
                String millpath = mill1.remove(0);
                ctx.outEventQueue.add(new InfoLineEvent("Fräsung wird gestartet."));
                if (millpath.startsWith("x")) {
                    millpath = millpath.substring(1);
                    ctx.arduinoF1.sendCommand("XPS:" + millpath + ",0,0,0;");
                    System.out.println("F1:" + ctx.arduinoF1.receiveResponse());
                } else if (millpath.startsWith("y")) {
                    millpath = millpath.substring(1);
                    ctx.arduinoF1.sendCommand("YPS:" + millpath + ",0,0,0;");
                    System.out.println("F1:" + ctx.arduinoF1.receiveResponse());
                } else if (millpath.startsWith("z")) {
                    millpath = millpath.substring(1);
                    int y = Integer.parseInt(millpath);
                    y+=100;
                    String s = String.valueOf(y);
                    ctx.arduinoF1.sendCommand("MOT:5,0,255 ,0;");
                    sleep();
                    ctx.arduinoF1.sendCommand("ZPS:" + s + ",255,0,0;");
                    System.out.println("F1:" + ctx.arduinoF1.receiveResponse());
                }
            }

            if(!ctx.taskStack.isEmpty() && ctx.taskStack.get(0) instanceof Mill) {
                mills = (Mill) ctx.taskStack.remove(0);
                ctx.outEventQueue.add(new InfoLineEvent("Nächste Fräsung wird Vorbereitet."));
                ctx.arduinoF1.sendCommand("ZNL;");
                System.out.println("F1:" + ctx.arduinoF1.receiveResponse());
                ctx.arduinoF1.sendCommand("XNL;");
                ctx.arduinoF1.sendCommand("YNL;");
                System.out.println("F1:" + ctx.arduinoF1.receiveResponse());
                System.out.println("F1:" + ctx.arduinoF1.receiveResponse());
            }else{
                mills = null;
            }
        }
        ctx.outEventQueue.add(new InfoLineEvent("Alle Fräsungen wurden durchgeführt."));
        ctx.arduinoF1.sendCommand("ZNL;");
        System.out.println(ctx.arduinoF1.receiveResponse());
        ctx.arduinoF1.sendCommand("MOT:5,0,0,0;");
        sleep();
        ctx.arduinoF1.sendCommand("XNL;");
        sleep();
        ctx.arduinoF1.sendCommand("YNL;");
        for(int i = 0; i <=1; i++) {
            System.out.println("F1:" + ctx.arduinoF1.receiveResponse());
        }
        ctx.arduinoR1.sendCommand("MOT:5,0,255,0;");
        sleep();
        ctx.arduinoF1.sendCommand("MOT:4,0,255,0;");
        sleep();
        ctx.arduinoR1.sendCommand("ZMX;");
        System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
        ctx.arduinoR1.sendCommand("ZNL;");
        System.out.println("R1:" + ctx.arduinoR1.receiveResponse());
        ctx.arduinoF1.sendCommand("XPS:170,0,0,0;");
        System.out.println("F1:" + ctx.arduinoF1.receiveResponse());
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
