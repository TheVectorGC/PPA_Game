package UnitBuilder;

import Unit.Unit;

public interface UnitBuilder {
    void setHealthPoints(int healthPoints);
    void setDefence(int defence);
    void setEvasion(int evasion);
    void setCriticalChance(int criticalChance);
    Unit build();
}
