package statemachine.states;

import communication.ArduinoConnection;

import purejavacomm.CommPortIdentifier;
import statemachine.Context;
import statemachine.events.ConnectionEvent;
import statemachine.events.FailedConnect;
import statemachine.events.InfoLineEvent;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ConnectionPhase implements State {
    private boolean sensor;

    public ConnectionPhase(boolean sensortest){
        sensor = sensortest;
    }
    public ConnectionPhase(){

    }

    @Override
    public State execute(Context ctx) {
        List<CommPortIdentifier> activePorts = Collections.list(CommPortIdentifier.getPortIdentifiers());
        List<ArduinoConnection> arduinoConnections = activePorts.stream()
            .filter((identifier) -> identifier.getPortType() == CommPortIdentifier.PORT_SERIAL)
            .map((identifier) -> {
                try {
                    return new ArduinoConnection(identifier);
                } catch (Exception e) {
                    Logger.getLogger("reactor").log(Level.WARNING, "Can't connect to COM port!", e);
                }
                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        ctx.arduinoB1 = arduinoConnections
                .stream()
                .filter((connection) -> "B1".equals(connection.getIdentifier()))
                .findFirst()
                .orElse(null);
        ctx.arduinoF1 = arduinoConnections
                .stream()
                .filter((connection) -> "F1".equals(connection.getIdentifier()))
                .findFirst()
                .orElse(null);
        ctx.arduinoR1 = arduinoConnections
                .stream()
                .filter((connection) -> "R1".equals(connection.getIdentifier()))
                .findFirst()
                .orElse(null);

        if(ctx.arduinoR1 == null || ctx.arduinoB1 == null || ctx.arduinoF1 == null){
            ctx.outEventQueue.add(
                    new FailedConnect(ctx.arduinoB1 == null,ctx.arduinoF1 == null,ctx.arduinoR1 == null)
            );
            if(sensor){
                return new SensorTest();
            }else {
                return new StartState();
            }
        }
        ctx.outEventQueue.add(new ConnectionEvent(ctx.arduinoB1 == null,ctx.arduinoF1 == null,ctx.arduinoR1 == null));
        ctx.outEventQueue.add(new InfoLineEvent("Alle 3 Arduinos wurden verbunden"));
        return new Connected();
    }
}
