package UnitBuilder;

import Game.UnitListener;
import Unit.Melee;
import Unit.Unit;

import java.util.List;

public class MeleeUnitBuilder implements UnitBuilder {
    private final Melee melee;
    public MeleeUnitBuilder() {
        melee = new Melee();
    }

    @Override
    public UnitBuilder setIsEnemy(boolean isEnemy) {
        melee.setEnemy(isEnemy);
        return this;
    }

    @Override
    public UnitBuilder setName(String name) {
        melee.setName(name);
        return this;
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
    public UnitBuilder setIsStunned(boolean isStunned) {
        melee.setStunned(isStunned);
        return this;
    }

    @Override
    public UnitBuilder setPosition(int position) {
        melee.setPosition(position);
        return this;
    }

    @Override
    public UnitBuilder setBleed(List<Integer> bleed) {
        melee.setBleed(bleed);
        return this;
    }

    @Override
    public Unit build() {
        UnitListener.addListeners(melee);
        return melee;
    }
}