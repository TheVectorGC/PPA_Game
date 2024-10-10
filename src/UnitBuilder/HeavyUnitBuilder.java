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
    public void setHealthPoints(int healthPoints) {
        heavy.setHealthPoints(healthPoints);
    }

    @Override
    public void setDefence(int defence) {
        heavy.setDefence(defence);
    }

    @Override
    public void setEvasion(int evasion) {
        heavy.setEvasion(evasion);
    }

    @Override
    public void setCriticalChance(int criticalChance) {
        heavy.setCriticalChance(criticalChance);
    }

    @Override
    public Unit build() {
        UnitListener.addListeners(heavy);
        return heavy;
    }
}
