package UnitsBuilders;

import Interfaces.UnitBuilder;
import Units.Wizard;
import Units.Unit;

public class WizardUnitBuilder implements UnitBuilder {
    private final Wizard wizard;
    public WizardUnitBuilder(boolean isEnemy) {
        wizard = new Wizard();
        wizard.setIsEnemy(isEnemy);
        if (isEnemy) wizard.setName("КОЛДУН-ВРАГ");
        else wizard.setName("КОЛДУН");
    }

    @Override
    public void setHealthPoints(int healthPoints) {
        wizard.setHealthPoints(healthPoints);
    }

    @Override
    public void setDefence(int defence) {
        wizard.setDefence(defence);
    }

    @Override
    public void setEvasion(int evasion) {
        wizard.setEvasion(evasion);
    }

    @Override
    public void setCriticalChance(int criticalChance) {
        wizard.setCriticalChance(criticalChance);
    }

    @Override
    public Unit build() {
        return wizard;
    }
}