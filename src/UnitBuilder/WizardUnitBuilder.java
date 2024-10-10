package UnitBuilder;

import Game.UnitListener;
import Unit.UnitType;
import Unit.Wizard;
import Unit.Unit;

public class WizardUnitBuilder implements UnitBuilder {
    private final Wizard wizard;
    public WizardUnitBuilder(boolean isEnemy) {
        wizard = new Wizard();
        wizard.setEnemy(isEnemy);
        wizard.setUnitType(UnitType.UNIT_WIZARD);
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
        UnitListener.addListeners(wizard);
        return wizard;
    }
}