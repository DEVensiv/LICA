package GUI;

import statemachine.Main;
import statemachine.commands.Connect;
import statemachine.commands.Start;
import statemachine.commands.StopSensor;
import statemachine.commands.TestSensor;
import statemachine.events.*;
import statemachine.events.Event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StageSwitch implements ActionListener {
    public LicaGUI licaGUI;
    public EditorGUI editorGUI;
    public Sensor sensor;
    boolean b1,f1,r1;


    public StageSwitch() {
        listener();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(licaGUI.start)) {
            Main.sendCMD(new Start());
        } else if (e.getSource().equals(licaGUI.B1sens)) {
            sensorB1();
            b1 = true;
        } else if (e.getSource().equals(licaGUI.F1sens)) {
            sensorF1();
            f1 = true;
        } else if (e.getSource().equals(licaGUI.R1sens)) {
            sensorR1();
            r1 = true;
        } else if (e.getSource().equals(sensor.stop)){
            stopSens();
        } else if (e.getSource().equals(licaGUI.Configer)){
            configBearb();
        } else if (e.getSource().equals(EditorGUI.closeer)){
            configClose();
        } else if (e.getSource().equals(EditorGUI.refresh)){
            visualizer();
        } else if (e.getSource().equals(EditorGUI.setPiece)){
            configSetPiece();
        }


    }

    void listener() {
        Thread worker = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Event e = Main.r.fetchEvent();
                    if (e instanceof InfoLineEvent) {
                        infolineeve(e);
                    } else if (e instanceof ConnectionEvent) {
                        connectionevent(e);
                    } else if (e instanceof FailedConnect) {
                        failconevent(e);
                    } else if (e instanceof ConnectionInterrupted) {
                        coninterrupt(e);
                    } else if (e instanceof Idle) {
                        idle(e);
                    }else if (e instanceof R1Sensor) {
                        setInfoR1(e);
                    }else if (e instanceof F1Sensor) {
                        setInfoF1(e);
                    }else if (e instanceof B1Sensor) {
                        setInfoB1(e);
                    }else if (e instanceof VisualizeEvent) {
                        visualizer1(e);
                    }

                }
            }
        });
        worker.start();
    }

    void connectionevent(Event e) {
        ConnectionEvent ce = (ConnectionEvent) e;
        if (!ce.isB1()) {
            licaGUI.B1stat.setText("Status: Verbunden");
            licaGUI.B1.setEnabled(true);
            licaGUI.B1stat.setEnabled(true);
            licaGUI.B1sens.setEnabled(true);
        }
        if (!ce.isF1()) {
            licaGUI.F1stat.setText("Status: Verbunden");
            licaGUI.F1.setEnabled(true);
            licaGUI.F1stat.setEnabled(true);
            licaGUI.F1sens.setEnabled(true);
        }
        if (!ce.isR1()) {
            licaGUI.R1stat.setText("Status: Verbunden");
            licaGUI.R1.setEnabled(true);
            licaGUI.R1stat.setEnabled(true);
            licaGUI.R1sens.setEnabled(true);
        }
        licaGUI.start.setEnabled(true);
    }

    void failconevent(Event e) {
        FailedConnect fe = (FailedConnect) e;
        if (!fe.isB1()) {
            licaGUI.B1stat.setText("Status: Verbunden");
            licaGUI.B1.setEnabled(true);
            licaGUI.B1stat.setEnabled(true);
            licaGUI.B1sens.setEnabled(true);
        }
        if (!fe.isF1()) {
            licaGUI.F1stat.setText("Status: Verbunden");
            licaGUI.F1.setEnabled(true);
            licaGUI.F1stat.setEnabled(true);
            licaGUI.F1sens.setEnabled(true);
        }
        if (!fe.isR1()) {
            licaGUI.R1stat.setText("Status: Verbunden");
            licaGUI.R1.setEnabled(true);
            licaGUI.R1stat.setEnabled(true);
            licaGUI.R1sens.setEnabled(true);
        }
        licaGUI.info.setText("Verbindungen konnten nicht alle aufgebaut werden.");
        licaGUI.start.setEnabled(false);
    }

    void coninterrupt(Event e) {
        try {
            licaGUI.info.setText("Verbindung verloren.");
            Thread.sleep(2000);
            licaGUI.info.setText("Bitte Starte das Programm neu!");
            Thread.sleep(5000);
            System.exit(0);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    void infolineeve(Event e) {
        try {
            InfoLineEvent ie = (InfoLineEvent) e;
            licaGUI.info.setText(ie.getInfo());
            Thread.sleep(2000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    void sensorR1() {
        sensor = new Sensor("Sensortest Roboter", this);
        Main.sendCMD(new TestSensor("R1"));
    }

    void setInfoR1(Event e) {
        if (!r1)
            return;

        R1Sensor se = (R1Sensor) e;
        sensor.D11d.setText(se.getD11());
        sensor.D13d.setText(se.getD13());
        sensor.A0d.setText(se.getA0());
        sensor.A1d.setText(se.getA1());
        sensor.A2d.setText(se.getA2());
        sensor.A3d.setText(se.getA3());
        sensor.A6d.setText(se.getA6());
        sensor.A7d.setText(se.getA7());
    }

    void sensorF1() {
        sensor = new Sensor("Sensortest Fräser", this);
        Main.sendCMD(new TestSensor("F1"));
    }

    void setInfoF1(Event e) {
        if (!f1)
            return;
        F1Sensor se = (F1Sensor) e;
        sensor.D11d.setText(se.getD11());
        sensor.D13d.setText(se.getD13());
        sensor.A0d.setText(se.getA0());
        sensor.A1d.setText(se.getA1());
        sensor.A2d.setText(se.getA2());
        sensor.A3d.setText(se.getA3());
        sensor.A6d.setText(se.getA6());
        sensor.A7d.setText(se.getA7());
    }

    void sensorB1() {
        sensor = new Sensor("Sensortest Bohrer", this);
        Main.sendCMD(new TestSensor("B1"));
    }

    void setInfoB1(Event e) {
        if (!b1)
            return;
        B1Sensor se = (B1Sensor) e;
        sensor.D11d.setText(se.getD11());
        sensor.D13d.setText(se.getD13());
        sensor.A0d.setText(se.getA0());
        sensor.A1d.setText(se.getA1());
        sensor.A2d.setText(se.getA2());
        sensor.A3d.setText(se.getA3());
        sensor.A6d.setText(se.getA6());
        sensor.A7d.setText(se.getA7());
    }

    void idle(Event e) {
        Idle ie = (Idle) e;
        if (!ie.isB1()) {
            licaGUI.B1sens.setEnabled(false);
        } else {
            licaGUI.B1sens.setEnabled(true);
        }
        if (!ie.isF1()) {
            licaGUI.F1sens.setEnabled(false);
        } else {
            licaGUI.F1sens.setEnabled(true);
        }
        if (!ie.isR1()) {
            licaGUI.R1sens.setEnabled(false);
        } else {
            licaGUI.R1sens.setEnabled(true);
        }
    }

    void stopSens(){
        sensor.close();
        Main.sendCMD(new StopSensor());
        Main.sendCMD(new Connect());
    }

    void configClose() {
        licaGUI.setVisible(true);
        editorGUI.setVisible(false);
        editorGUI.removeAll();
    }

    void configBearb(){
        licaGUI.setVisible(false);
        editorGUI = new EditorGUI(this);
        editorGUI.setVisible(true);
    }

    void visualizer() {
        String id = editorGUI.pieceidinput.getText();
        new VisualGUI(id);
    }

    void configSetPiece(){
        editorGUI.clear.setEnabled(true);
        editorGUI.adddrill.setEnabled(true);
        editorGUI.pieceidinput.setEnabled(false);
    }

    void visualizer1(Event e){
        VisualizeEvent v = (VisualizeEvent) e;
        new VisualGUI(v.getFromArdu());
    }
}
