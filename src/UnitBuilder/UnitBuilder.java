package UnitBuilder;

import Unit.Unit;

import java.util.List;

public interface UnitBuilder {
    UnitBuilder setIsEnemy(boolean isEnemy);
    UnitBuilder setName(String name);
    UnitBuilder setHealthPoints(int healthPoints);
    UnitBuilder setDefence(int defence);
    UnitBuilder setEvasion(int evasion);
    UnitBuilder setCriticalChance(int criticalChance);
    UnitBuilder setIsStunned(boolean isStunned);
    UnitBuilder setPosition(int position);
    UnitBuilder setBleed(List<Integer> bleed);
    Unit build();
}
