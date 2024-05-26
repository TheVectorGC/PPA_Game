package Observers;

import Interfaces.UnitObserver;
import Units.UnitState;

public class UnitsObserver implements UnitObserver {
    public void update(UnitState[] oldStates, UnitState[] newStates) {
        for (int i = 0; i < oldStates.length; i++) {

        }
    }

    private void logChangedState(UnitState oldState, UnitState newState) {

    }
}
