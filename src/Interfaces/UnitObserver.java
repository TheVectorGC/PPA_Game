package Interfaces;

import Units.UnitState;

public interface UnitObserver {
    void update(UnitState[] oldStates, UnitState[] newStates);
}
