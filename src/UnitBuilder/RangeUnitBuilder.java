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
    public void setHealthPoints(int healthPoints) {
        range.setHealthPoints(healthPoints);
    }

    @Override
    public void setDefence(int defence) {
        range.setDefence(defence);
    }

    @Override
    public void setEvasion(int evasion) {
        range.setEvasion(evasion);
    }

    @Override
    public void setCriticalChance(int criticalChance) {
        range.setCriticalChance(criticalChance);
    }

    @Override
    public Unit build() {
        UnitListener.addListeners(range);
        return range;
    }
}