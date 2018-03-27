package config;

import statemachine.Context;

import java.io.IOException;

public interface Task {
    void executeTask(Context ctx) throws IOException;
}
