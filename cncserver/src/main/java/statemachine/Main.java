package statemachine;

import GUI.EditorGUI;
import GUI.LicaGUI;
import GUI.StageSwitch;
import config.Config;
import config.Task;
import config.WorkpieceID;
import statemachine.commands.Command;
import statemachine.commands.Connect;
import statemachine.states.StartState;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static StageSwitch l;
    public static Reactor r;
    static Config cfg;
    public static void main(String[] args){
        try {
            cfg = new Config(new File("data.txt"));
        } catch (IOException e) {
            Logger.getLogger("main").log(Level.CONFIG, "Config not found!");
            JOptionPane.showMessageDialog(new JFrame(),
                    "Config cant be read.\nPlease restart the Programm and look for the 'data.txt'",
                    "Main ERROR", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        r = new Reactor(new StartState(),cfg);
        l = new StageSwitch();
        l.licaGUI = new LicaGUI(l);
        l.editorGUI = new EditorGUI(l);
        sendCMD(new Connect());

    }

    public static HashMap<WorkpieceID, List<Task>> getConfig(){
        return cfg.getConfig();
    }

    public static void setConfig(Config config){
        cfg = config;
        r.setConfig(cfg);
    }

    public static void sendCMD(Command cmd){

        r.sendCommand(cmd);

    }

    public static void makeAlert(String message, String title){

        JOptionPane.showMessageDialog(new JFrame(),
                message,
                title, JOptionPane.ERROR_MESSAGE);

    }

}
