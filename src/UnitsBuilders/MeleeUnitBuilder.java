package UnitsBuilders;

import Interfaces.UnitBuilder;
import Units.Melee;
import Units.Unit;

public class MeleeUnitBuilder implements UnitBuilder {
    private final Melee melee;
    public MeleeUnitBuilder(boolean isEnemy) {
        melee = new Melee();
        melee.setIsEnemy(isEnemy);
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
        return melee;
    }
}