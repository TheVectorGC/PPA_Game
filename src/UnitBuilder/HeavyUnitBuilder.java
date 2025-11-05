package UnitBuilder;

import Game.UnitListener;
import Unit.Heavy;
import Unit.Unit;
import Unit.UnitType;

public class HeavyUnitBuilder implements UnitBuilder {
    private final Heavy heavy;
    public HeavyUnitBuilder(boolean isEnemy) {
        heavy = new Heavy();
        heavy.setEnemy(isEnemy);
        heavy.setUnitType(UnitType.UNIT_HEAVY);
        if (isEnemy) heavy.setName("ХЭВИК-ВРАГ");
        else heavy.setName("ХЭВИК");
    }

    @Override
    public UnitBuilder setHealthPoints(int healthPoints) {
        heavy.setHealthPoints(healthPoints);
        return this;
    }

    @Override
    public UnitBuilder setDefence(int defence) {
        heavy.setDefence(defence);
        return this;
    }

    @Override
    public UnitBuilder setEvasion(int evasion) {
        heavy.setEvasion(evasion);
        return this;
    }

    @Override
    public UnitBuilder setCriticalChance(int criticalChance) {
        heavy.setCriticalChance(criticalChance);
        return this;
    }

    @Override
    public Unit build() {
        UnitListener.addListeners(heavy);
        return heavy;
    }
}
