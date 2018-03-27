package GUI;

import config.WorkpieceID;

import javax.swing.*;

public class VisualGUI extends JFrame {
    public VisualGUI(String input) {

        Visualize v = new Visualize(new WorkpieceID(input));
        setSize(720, 720);
        setAutoRequestFocus(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        add(v);
        setLocationRelativeTo(null);
        setTitle("Grafik der Aktuellen Config");
    }
}
