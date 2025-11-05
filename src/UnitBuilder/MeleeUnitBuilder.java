package UnitBuilder;

import Game.UnitListener;
import Unit.Melee;
import Unit.Unit;
import Unit.UnitType;

public class MeleeUnitBuilder implements UnitBuilder {
    private final Melee melee;
    public MeleeUnitBuilder(boolean isEnemy) {
        melee = new Melee();
        melee.setEnemy(isEnemy);
        melee.setUnitType(UnitType.UNIT_MELEE);
        if (isEnemy) melee.setName("БЛИЖНИК-ВРАГ");
        else melee.setName("БЛИЖНИК");
    }

    @Override
    public UnitBuilder setHealthPoints(int healthPoints) {
        melee.setHealthPoints(healthPoints);
        return this;
    }

    @Override
    public UnitBuilder setDefence(int defence) {
        melee.setDefence(defence);
        return this;
    }

    @Override
    public UnitBuilder setEvasion(int evasion) {
        melee.setEvasion(evasion);
        return this;
    }

    @Override
    public UnitBuilder setCriticalChance(int criticalChance) {
        melee.setCriticalChance(criticalChance);
        return this;
    }

    @Override
    public Unit build() {
        UnitListener.addListeners(melee);
        return melee;
    }
}