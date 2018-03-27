package config;

public class WorkpieceID {

    private Integer value;

    public int getValue (){
        return value;
    }

    public WorkpieceID(int value){
        if(value <= 6 && value >=0){
            this.value = value;
            return;
        }
        throw new IllegalArgumentException("Workpiece ID isn't in range.");
    }

    public WorkpieceID(String fromArduino) {
        String[] splitted = fromArduino.split(",");
        value = 0;
        if(splitted.length == 3) {
            for (int a = 0; a <= 2; a++) {
                value += splitted[a].equals("1") ? 1 << (2 - a) : 0;
            }
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
}
