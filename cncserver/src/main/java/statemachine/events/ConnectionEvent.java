package statemachine.events;

public class ConnectionEvent implements Event{
boolean stateB1,stateF1,stateR1;

    public boolean isB1() {
        return stateB1;
    }

    public boolean isF1() {
        return stateF1;
    }

    public boolean isR1() {
        return stateR1;
    }

    public ConnectionEvent(boolean B1, boolean F1, boolean R1){

        this.stateB1 = B1;
        this.stateF1 = F1;
        this.stateR1 = R1;

    }
}
