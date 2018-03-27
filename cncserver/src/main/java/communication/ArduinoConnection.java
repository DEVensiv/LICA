package communication;

import purejavacomm.*;

import java.io.*;
import java.util.TooManyListenersException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArduinoConnection implements SerialPortEventListener {
    private SerialPort serialConnection;
    private BufferedReader reader;
    private Writer writer;
    private LinkedBlockingQueue<String> incomingMessages;
    private String identifier;

    public ArduinoConnection(CommPortIdentifier comPort) throws PortInUseException, IOException, UnsupportedCommOperationException, TooManyListenersException {
        if (comPort.getPortType() != CommPortIdentifier.PORT_SERIAL) {
            throw new RuntimeException("Port has to be a serial port!");
        }
        serialConnection = (SerialPort) comPort.open("cnc", 1000);

        serialConnection.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);

        reader = new BufferedReader(new InputStreamReader(serialConnection.getInputStream()));
        writer = new OutputStreamWriter(serialConnection.getOutputStream());
        incomingMessages = new LinkedBlockingQueue<>();

        serialConnection.addEventListener(this);

        serialConnection.notifyOnDataAvailable(true);
    }

    public void sendCommand(String cmd) throws IOException {
        writer.write(cmd);
        writer.flush();
    }

    public String receiveResponse(){

        try {
            return incomingMessages.take();
        } catch (InterruptedException e) {
            throw new RuntimeException("WILL NEVER HAPPEND", e);
        }

    }

    public String receiveResponse(int timeout){

        try {
            return incomingMessages.poll(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException("WILL NEVER HAPPEND", e);
        }

    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if (serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                do {
                    String line = reader.readLine();
                    if (identifier == null) {
                        identifier = line;
                    } else {
                        incomingMessages.add(line);
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (reader.ready());
            } catch (IOException e) {
                Logger.getLogger("reactor").log(Level.WARNING, "Couldn't read line", e);
            }
        }

    }

    public String getIdentifier() {
        int counter = 0;
        while(identifier == null && counter < 30){
            try {
                Thread.sleep(100);
                counter++;
            } catch (InterruptedException e) {
                Logger.getLogger("reactor").log(Level.WARNING, "Thread sleep interrupted.", e);
            }
        }
        return identifier;
    }

    public void close() {
        serialConnection.close();
    }
}
