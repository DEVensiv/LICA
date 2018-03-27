package statemachine.events;

public class R1Sensor implements Event{
    String a0,a1,a2,a3,a6,a7,d11,d13;
    public R1Sensor(String input){
        String[] list;
        list = input.split(",");
        this.a0 = list[1];
        this.a1 = list[2];
        this.a2 = list[3];
        this.a3 = list[4];
        this.a6 = list[5];
        this.a7 = list[6];
        this.d11 = list[7];
        this.d13 = list[8];
    }
    public String getA0() {
        return a0;
    }

    public String getA1() {
        return a1;
    }

    public String getA2() {
        return a2;
    }

    public String getA3() {
        return a3;
    }

    public String getA6() {
        return a6;
    }

    public String getA7() {
        return a7;
    }

    public String getD11() {
        return d11;
    }

    public String getD13() {
        return d13;
    }
}
