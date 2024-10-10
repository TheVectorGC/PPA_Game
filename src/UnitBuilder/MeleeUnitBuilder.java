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
    public void setHealthPoints(int healthPoints) {
        melee.setHealthPoints(healthPoints);
    }

    @Override
    public void setDefence(int defence) {
        melee.setDefence(defence);
    }

    @Override
    public void setEvasion(int evasion) {
        melee.setEvasion(evasion);
    }

    @Override
    public void setCriticalChance(int criticalChance) {
        melee.setCriticalChance(criticalChance);
    }

    @Override
    public Unit build() {
        UnitListener.addListeners(melee);
        return melee;
    }
}