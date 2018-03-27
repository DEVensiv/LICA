package communication;

public interface ArduinoResponse {
    ArduinoResponse fromBytes(byte[] received);
}
