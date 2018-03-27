package statemachine.events;

public class Idle implements Event{
    private boolean B1;
    private boolean F1;
    private boolean R1;
    public Idle(boolean B1, boolean F1, boolean R1){
        this.B1 = B1;
        this.F1 = F1;
        this.R1 = R1;
    }

    public boolean isB1() {
        return B1;
    }

    public boolean isF1() {
        return F1;
    }

    public boolean isR1() {
        return R1;
    }
}
