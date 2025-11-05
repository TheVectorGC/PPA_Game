package UnitBuilder;

import Unit.Unit;

public interface UnitBuilder {
    UnitBuilder setHealthPoints(int healthPoints);
    UnitBuilder setDefence(int defence);
    UnitBuilder setEvasion(int evasion);
    UnitBuilder setCriticalChance(int criticalChance);
    Unit build();
}
