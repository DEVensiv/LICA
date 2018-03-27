package GUI;

import config.Config;

import javax.swing.*;

public class LicaGUI extends JFrame{

    private static final long serialVersionUID = 1L;
    public static JButton toMain,start,weiter;
    public static JButton B1sens,F1sens,R1sens;
    public static JTextField info;
    public static JButton Configer;
    public static JTextArea B1,F1,R1,B1stat,F1stat,R1stat;

    public LicaGUI(StageSwitch stageSwitch) {
        setLayout(null);
        setSize(500, 500);
        setAutoRequestFocus(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(false);
        setLocationRelativeTo(null);
        setTitle("LICA");
        init(stageSwitch);
        add(B1);
        add(F1);
        add(R1);
        add(B1stat);
        add(F1stat);
        add(R1stat);
        add(weiter);
        add(start);
        add(info);
        add(toMain);
        add(B1sens);
        add(F1sens);
        add(R1sens);
        add(Configer);
        setVisible(true);
        setLocationRelativeTo(null);
        repaint();
    }
    void init(StageSwitch stageSwitch){
        start = new JButton();
        info = new JTextField("Infos");
        toMain = new JButton();
        weiter = new JButton();
        B1sens = new JButton();
        F1sens = new JButton();
        R1sens = new JButton();
        B1 = new JTextArea("Bohrer");
        B1stat = new JTextArea("Satus: Nicht verbunden");
        F1 = new JTextArea("Fräser");
        F1stat = new JTextArea("Satus: Nicht verbunden");
        R1 = new JTextArea("Roboter");
        R1stat = new JTextArea("Satus: Nicht verbunden");
        info.setText("Infos");
        B1.setBounds(50,50,100,20);
        B1.setEditable(false);
        B1stat.setBounds(160,50,200,20);
        B1stat.setEditable(false);
        F1.setBounds(50,100,100,20);
        F1.setEditable(false);
        F1stat.setBounds(160,100,200,20);
        F1stat.setEditable(false);
        R1.setBounds(50,150,100,20);
        R1.setEditable(false);
        R1stat.setBounds(160,150,200,20);
        R1stat.setEditable(false);
        B1.setEnabled(false);
        F1.setEnabled(false);
        R1.setEnabled(false);
        B1stat.setEnabled(false);
        F1stat.setEnabled(false);
        R1stat.setEnabled(false);
        B1sens.setBounds(380,50,100,20);
        F1sens.setBounds(380,100,100,20);
        R1sens.setBounds(380,150,100,20);
        B1sens.setText("Sensortest");
        F1sens.setText("Sensortest");
        R1sens.setText("Sensortest");
        B1sens.setEnabled(false);
        F1sens.setEnabled(false);
        R1sens.setEnabled(false);
        B1sens.addActionListener(stageSwitch);
        F1sens.addActionListener(stageSwitch);
        R1sens.addActionListener(stageSwitch);
        info.setBounds(100,400,300,20);
        start.setBounds(200,300,100,20);
        start.setText("Start");
        start.addActionListener(stageSwitch);
        start.setEnabled(false);
        Configer = new JButton();
        Configer.setBounds(20,20,100,20);
        Configer.setText("Config");
        //Configer.setEnabled(false);
        Configer.addActionListener(stageSwitch);
    }

}
