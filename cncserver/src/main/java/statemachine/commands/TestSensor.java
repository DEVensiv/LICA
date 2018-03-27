package statemachine.commands;

public class TestSensor implements Command{
    private String arduino;

    public TestSensor(String arduino) {
        this.arduino = arduino;
    }

    public String getName() {
        return arduino;
    }
}
