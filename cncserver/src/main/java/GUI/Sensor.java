package GUI;

import javax.swing.*;

public class Sensor extends JFrame{
    public static JTextArea D11,A7,A6,A3,A2,A1,A0,D13,D11d,A7d,A6d,A3d,A2d,A1d,A0d,D13d;
    public static JButton stop;

    public Sensor(String title, StageSwitch stageSwitch){
        setLayout(null);
        setSize(500, 500);
        setAutoRequestFocus(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle(title);
        D11 = new JTextArea("D11");
        A7 = new JTextArea("A7");
        A6 = new JTextArea("A6");
        A3 = new JTextArea("A3");
        A2 = new JTextArea("A2");
        A1 = new JTextArea("A1");
        A0 = new JTextArea("A0");
        D13 = new JTextArea("D13");
        D11.setBounds(220,20,30,20);
        A7.setBounds(220,80,30,20);
        A6.setBounds(220,140,30,20);
        A3.setBounds(220,200,30,20);
        A2.setBounds(220,260,30,20);
        A1.setBounds(220,320,30,20);
        A0.setBounds(220,380,30,20);
        D13.setBounds(220,440,30,20);
        D11.setEditable(false);
        A7.setEditable(false);
        A6.setEditable(false);
        A3.setEditable(false);
        A2.setEditable(false);
        A1.setEditable(false);
        A0.setEditable(false);
        D13.setEditable(false);
        add(D11);
        add(A7);
        add(A6);
        add(A3);
        add(A2);
        add(A1);
        add(A0);
        add(D13);
        D11d = new JTextArea("D11");
        A7d = new JTextArea("A7");
        A6d = new JTextArea("A6");
        A3d = new JTextArea("A3");
        A2d = new JTextArea("A2");
        A1d = new JTextArea("A1");
        A0d = new JTextArea("A0");
        D13d = new JTextArea("D13");
        D11d.setBounds(260,20,30,20);
        A7d.setBounds(260,80,30,20);
        A6d.setBounds(260,140,30,20);
        A3d.setBounds(260,200,30,20);
        A2d.setBounds(260,260,30,20);
        A1d.setBounds(260,320,30,20);
        A0d.setBounds(260,380,30,20);
        D13d.setBounds(260,440,30,20);
        D11d.setEditable(false);
        A7d.setEditable(false);
        A6d.setEditable(false);
        A3d.setEditable(false);
        A2d.setEditable(false);
        A1d.setEditable(false);
        A0d.setEditable(false);
        D13d.setEditable(false);
        stop = new JButton();
        stop.setBounds(380,20,100, 20);
        stop.setText("Stoppen");
        stop.addActionListener(stageSwitch);
        add(stop);
        add(D11d);
        add(A7d);
        add(A6d);
        add(A3d);
        add(A2d);
        add(A1d);
        add(A0d);
        add(D13d);
        setVisible(true);
        repaint();
    }

    int close(){
        setVisible(false);
        return 0;
    }

}
