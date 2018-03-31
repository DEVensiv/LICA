package config;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkpieceID {

    private Integer value;
    private boolean valid;

    public int getValue (){
        return value;
    }

    public WorkpieceID(int value){
        if(value <= 6 && value >=0){
            this.value = value;
            valid = true;
            return;
        }
        Logger.getLogger("reactor").log(Level.SEVERE, "Workpice ID isn't in Range");
        JOptionPane.showMessageDialog(new JFrame(),
                "Got an wrong ID.\nThere can be no Config for this ID",
                "Wrong Workpice ID", JOptionPane.ERROR_MESSAGE);
        valid = false;
    }

    public WorkpieceID(String fromArduino) {
        String[] splitted = fromArduino.split(",");
        value = 0;
        if(splitted.length == 3) {
            for (int a = 0; a <= 2; a++) {
                value += splitted[a].equals("1") ? 1 << (2 - a) : 0;
                if (value <= 6 && value > 0)
                    valid = true;
            }
        }
        if (value == 0){
            Logger.getLogger("reactor").log(Level.SEVERE, "gotten String isnt valid");
            JOptionPane.showMessageDialog(new JFrame(),
                    "Got an wrong ID.\nGiven ID isn't valid!",
                    "Wrong Workpice ID", JOptionPane.ERROR_MESSAGE);
            valid = false;
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  WorkpieceID) {
            WorkpieceID other = (WorkpieceID) obj;
            return value.equals(other.value);
        }
        return false;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public boolean isValid() {
        return valid;
    }
}
