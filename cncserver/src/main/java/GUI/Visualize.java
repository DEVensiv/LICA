package GUI;

import config.*;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class Visualize extends Canvas {
    private WorkpieceID WpID;
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.gray);
        g.drawString("Der graue Bereich repräsentiert das Werkstück", 200,170);
        g.drawString("Werkstück nummer: " + WpID.getValue(), 40,170);
        g.setColor(Color.red);
        g.drawString("Die roten Bereiche repräsentieen das zu Bohrende", 200,180);
        g.setColor(Color.green);
        g.drawString("Die grünen Bereiche repräsentieen das zu Fräsende", 200,190);
        g.setColor(Color.gray);
        g.fillRect(200,200,340,340);
        Config cfg = null;
        try {
            cfg = cfg = new Config(new File("data.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Draw to ID " + WpID.getValue());
        while (!cfg.getTasks(WpID).isEmpty()) {
            Task task = cfg.getTasks(WpID).remove(0);
            if(task instanceof Drill){
                g.setColor(new Color(1.0f,0.0f,0.0f,0.5f));
                Drill drill = (Drill) task;
                g.fillOval(drill.getX()/2 + 183,drill.getY()/2 + 183,34,34);
                System.out.println("Draw drill " + drill.getX() + "," + drill.getY());
            }
            if(task instanceof Mill){
                g.setColor(new Color(0.0f,1.0f,0.0f,0.5f));
                Mill mill = (Mill) task;
                List<String> mills = mill.getMill();
                boolean down = false;
                int xnow = 0,xstart = 0;
                int ynow = 0,ystart = 0;
                boolean move = false;
                while (!mills.isEmpty()) {
                    String millpath = mills.remove(0);
                    if (millpath.startsWith("x")) {
                        xnow = Integer.parseInt(millpath.substring(1));
                        if(move){
                            g.fillRect(xstart/2 + 200,ystart/2 + 183,xnow/2-xstart/2, 37);
                            System.out.println("Draw mill " + (xstart/2 + 200) + "," + (ystart/2 + 200) + ","  + (xnow-xstart/2) + ","  + 37);
                            xstart = xnow;
                        }
                    } else if (millpath.startsWith("y")) {
                        ynow = Integer.parseInt(millpath.substring(1));
                        if(move){
                            g.fillRect(xstart/2 + 183,ystart/2 + 200,37, ynow/2-ystart/2);
                            System.out.println("Draw mill " + (xstart/2 + 200) + "," + (ystart/2 + 200) + ","  + (xnow-xstart/2) + ","  + 37);
                            ystart = ynow;
                        }
                    } else if (millpath.startsWith("z")) {
                        xstart = xnow;
                        ystart = ynow;

                        move = true;
                    }
                }
            }

        }

    }
    public Visualize(WorkpieceID WpID){
        this.WpID = WpID;
    }
}
