package GUI;

import config.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class VisualizeInfo extends Canvas {

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.gray);
        g.drawString("Der graue Bereich repräsentiert das Werkstück", 200,170);
        g.setColor(Color.red);
        g.drawString("Die roten Bereiche repräsentieen das zu Bohrende", 200,180);
        g.setColor(Color.green);
        g.drawString("Die grünen Bereiche repräsentieen das zu Fräsende", 200,190);
    }

    public VisualizeInfo(){

    }

    @Override
    public Graphics getGraphics() {
        return super.getGraphics();
    }
}
