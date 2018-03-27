package statemachine.events;

public class FailedConnect implements Event{

    private boolean b1;
    private boolean f1;
    private boolean r1;

    public boolean isB1() {
        return b1;
    }

    public boolean isF1() {
        return f1;
    }

    public boolean isR1() {
        return r1;
    }


    public FailedConnect(boolean B1,boolean B2,boolean R1) {
        b1 = B1;
        f1 = B2;
        r1 = R1;
    }

}
