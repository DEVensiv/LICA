package statemachine.events;

public class InfoLineEvent implements Event {
String info;

    public InfoLineEvent(String info) {
        this.info = info;
    }
public String getInfo(){
        return info;
}
}
