package GUI;

import javax.swing.*;

public class EditorGUI extends JFrame {

    public static JButton closeer, refresh, clear, setPiece, adddrill, addmill;
    public static JTextField pieceidinput, pieceinfo, xin, yin, zin, speedin;

    public EditorGUI(StageSwitch stageSwitch) {
        setSize(500, 500);
        setAutoRequestFocus(true);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(false);
        setLocationRelativeTo(null);
        setTitle("Config bearbeiten");
        init(stageSwitch);
        add(closeer);
        //add(clear);
        add(setPiece);
        add(refresh);
        //add(adddrill);
        //add(addmill);
        //add(xin);
        //add(yin);
        //add(zin);
        //add(speedin);
        add(pieceidinput);
        add(pieceinfo);
        setVisible(false);
        repaint();
    }

    void init(StageSwitch stageSwitch) {
        pieceinfo = new JTextField("Geben sie die Id eines Strichcodes ein Bsp. '0,0,1'");
        pieceinfo.setBounds(20,20,300,20);
        pieceinfo.setEditable(false);
        pieceidinput = new JTextField("0,0,1");
        pieceidinput.setBounds(20, 60, 60, 20);
        refresh = new JButton("Grafik Darstellen");
        refresh.setBounds(330, 420, 150, 20);
        refresh.addActionListener(stageSwitch);
        setPiece = new JButton("Auswählen");
        setPiece.setBounds(80, 60, 100, 20);
        setPiece.addActionListener(stageSwitch);
        clear = new JButton("Aufgaben löschen");
        clear.setBounds(330, 60, 150, 20);
        clear.setEnabled(false);
        clear.addActionListener(stageSwitch);
        xin = new JTextField("X Position");
        xin.setBounds(20, 100, 65, 20);
        yin = new JTextField("X Position");
        yin.setBounds(85, 100, 65, 20);
        zin = new JTextField("X Position");
        zin.setBounds(150, 100, 65, 20);
        speedin = new JTextField("Speed (50-255)");
        speedin.setBounds(215, 100, 95, 20);
        adddrill = new JButton("Bohr Punkt hinzufügen");
        adddrill.setBounds(310, 100, 170, 20);
        adddrill.setEnabled(false);
        adddrill.addActionListener(stageSwitch);

        closeer = new JButton();
        closeer.setBounds(380, 20, 100, 20);
        closeer.setText("Zurück");
        closeer.addActionListener(stageSwitch);


    }

}

