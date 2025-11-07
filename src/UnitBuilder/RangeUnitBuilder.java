package UnitBuilder;

import Game.UnitListener;
import Unit.Range;
import Unit.Unit;

import java.util.List;

public class RangeUnitBuilder implements UnitBuilder {
    private final Range range;
    public RangeUnitBuilder() {
        range = new Range();
    }

    @Override
    public UnitBuilder setIsEnemy(boolean isEnemy) {
        range.setEnemy(isEnemy);
        return this;
    }

    @Override
    public UnitBuilder setName(String name) {
        range.setName(name);
        return this;
    }

    @Override
    public UnitBuilder setHealthPoints(int healthPoints) {
        range.setHealthPoints(healthPoints);
        return this;
    }

    @Override
    public UnitBuilder setDefence(int defence) {
        range.setDefence(defence);
        return this;
    }

    @Override
    public UnitBuilder setEvasion(int evasion) {
        range.setEvasion(evasion);
        return this;
    }

    @Override
    public UnitBuilder setCriticalChance(int criticalChance) {
        range.setCriticalChance(criticalChance);
        return this;
    }

    @Override
    public UnitBuilder setIsStunned(boolean isStunned) {
        range.setStunned(isStunned);
        return this;
    }

    @Override
    public UnitBuilder setPosition(int position) {
        range.setPosition(position);
        return this;
    }

    @Override
    public UnitBuilder setBleed(List<Integer> bleed) {
        range.setBleed(bleed);
        return this;
    }

    @Override
    public Unit build() {
        UnitListener.addListeners(range);
        return range;
    }
}