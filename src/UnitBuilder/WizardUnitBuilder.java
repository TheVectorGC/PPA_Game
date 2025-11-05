package UnitBuilder;

import Game.UnitListener;
import Unit.Wizard;
import Unit.Unit;

import java.util.List;

public class WizardUnitBuilder implements UnitBuilder {
    private final Wizard wizard;
    public WizardUnitBuilder() {
        wizard = new Wizard();
    }

    @Override
    public UnitBuilder setIsEnemy(boolean isEnemy) {
        wizard.setEnemy(isEnemy);
        return this;
    }

    @Override
    public UnitBuilder setName(String name) {
        wizard.setName(name);
        return this;
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
    public UnitBuilder setIsStunned(boolean isStunned) {
        wizard.setStunned(isStunned);
        return this;
    }

    @Override
    public UnitBuilder setPosition(int position) {
        wizard.setPosition(position);
        return this;
    }

    @Override
    public UnitBuilder setBleed(List<Integer> bleed) {
        wizard.setBleed(bleed);
        return this;
    }

    @Override
    public Unit build() {
        UnitListener.addListeners(wizard);
        return wizard;
    }
}