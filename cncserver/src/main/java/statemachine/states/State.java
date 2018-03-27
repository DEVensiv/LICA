package statemachine.states;

import statemachine.Context;

public interface State {
    State execute(Context ctx);
}
