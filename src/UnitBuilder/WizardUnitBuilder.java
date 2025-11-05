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
    public UnitBuilder setHealthPoints(int healthPoints) {
        wizard.setHealthPoints(healthPoints);
        return this;
    }

    @Override
    public UnitBuilder setDefence(int defence) {
        wizard.setDefence(defence);
        return this;
    }

    @Override
    public UnitBuilder setEvasion(int evasion) {
        wizard.setEvasion(evasion);
        return this;
    }

    @Override
    public UnitBuilder setCriticalChance(int criticalChance) {
        wizard.setCriticalChance(criticalChance);
        return this;
    }

    @Override
    public Unit build() {
        UnitListener.addListeners(wizard);
        return wizard;
    }
}