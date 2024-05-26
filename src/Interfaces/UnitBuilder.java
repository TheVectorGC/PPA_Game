package Interfaces;

import Units.Unit;
import java.util.ArrayList;

public interface UnitBuilder {
    void setHealthPoints(int healthPoints);
    void setDefence(int defence);
    void setEvasion(int evasion);
    void setCriticalChance(int criticalChance);
    Unit build();
}
