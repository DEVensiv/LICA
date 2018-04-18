package GUI;

import javafx.stage.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

public class InfoGUI extends JFrame implements ActionListener{

    JButton credits;
    JButton close;
    VisualizeInfo canvas;

    public InfoGUI(){
        setLayout(null);
        setUndecorated(true);
        setSize(700,500);
        setAutoRequestFocus(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setTitle("Info");
        init();
   //     add(canvas);
        add(credits);
        add(close);
    }

    void init(){
        credits = new JButton();
        close = new JButton();
        canvas = new VisualizeInfo();
        close.setBounds(680,0,20,20);
        close.setText("");
        close.setIcon(new ImageIcon("./resource/XButton.png"));
        close.addActionListener(this);
        close.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        credits.setBounds(600, 480, 100,20);
        credits.setText("Credits");
        credits.addActionListener(this);
        credits.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(close))
            this.dispose();
        if (e.getSource().equals(credits))
            credits.setEnabled(false);
    }
}
