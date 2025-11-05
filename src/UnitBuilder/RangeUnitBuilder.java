package UnitBuilder;

import Game.UnitListener;
import Unit.Range;
import Unit.Unit;
import Unit.UnitType;

public class RangeUnitBuilder implements UnitBuilder {
    private final Range range;
    public RangeUnitBuilder(boolean isEnemy) {
        range = new Range();
        range.setEnemy(isEnemy);
        range.setUnitType(UnitType.UNIT_RANGE);
        if (isEnemy) range.setName("ДАЛЬНИК-ВРАГ");
        else range.setName("ДАЛЬНИК");
    }

    @Override
    public UnitBuilder setHealthPoints(int healthPoints) {
        range.setHealthPoints(healthPoints);
        return this;
    }

    @Override
    public UnitBuilder setDefence(int defence) {
        range.setDefence(defence);
        return this;
    }

    @Override
    public UnitBuilder setEvasion(int evasion) {
        range.setEvasion(evasion);
        return this;
    }

    @Override
    public UnitBuilder setCriticalChance(int criticalChance) {
        range.setCriticalChance(criticalChance);
        return this;
    }

    @Override
    public Unit build() {
        UnitListener.addListeners(range);
        return range;
    }
}